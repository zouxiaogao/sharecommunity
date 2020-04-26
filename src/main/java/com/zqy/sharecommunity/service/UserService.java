package com.zqy.sharecommunity.service;

import com.zqy.sharecommunity.dao.LoginTicketMapper;
import com.zqy.sharecommunity.dao.UserMapper;
import com.zqy.sharecommunity.entity.LoginTicket;
import com.zqy.sharecommunity.entity.User;
import com.zqy.sharecommunity.util.CommunityConstant;
import com.zqy.sharecommunity.util.CommunityUtil;
import com.zqy.sharecommunity.util.MailClient;
import com.zqy.sharecommunity.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author zqy
 * @Date 2019/12/02
 */
@Service
public class UserService implements CommunityConstant {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private RedisTemplate redisTemplate;

//    @Autowired
//    private LoginTicketMapper loginTicketMapper;

    @Value("${sharecommunity.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;


    public User findUserByUserId(int userId){

        User user = getCache(userId);
        if(user == null){
            user=initCache(userId);
        }

        return user;

        //return userMapper.selectByUserId(userId);
    }


    /**
     * 注册
     * **/
    public Map<String,Object> register(User user){
        Map<String,Object> map=new HashMap<>();

        //空值处理
        if(user==null){
            throw new IllegalArgumentException("参数不能为空！");
        }
        if(StringUtils.isBlank(user.getUsername())){
            map.put("usernameMsg","账号不能为空！");
            return  map;
        }
        if(StringUtils.isBlank(user.getPassword())){
            map.put("passwordMsg","密码不能为空！");
            return  map;
        }
        if(StringUtils.isBlank(user.getEmail())){
            map.put("emailMsg","邮箱不能为空！");
            return  map;
        }

        if (user.getPassword().length()<6 || user.getPassword().length()>11){
            map.put("passwordMsg","密码位数必须在6-11之间！");
            return  map;
        }

        //验证账号
        User u = userMapper.selectByName(user.getUsername());
        if(u!=null){
            map.put("usernameMsg","该账号已存在！");
            return map;
        }

        //验证邮箱
        u = userMapper.selectByEmail(user.getEmail());
        if(u!=null){
            map.put("emailMsg","该邮箱已被注册！");
            return map;
        }

        //注册用户
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5(user.getPassword()+user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(domain+contextPath+"/img/icon.png");
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        // 激活邮件
        Context context=new Context();
        context.setVariable("email",user.getEmail());
        String url=domain+contextPath+"/activation/"+user.getId()+"/"+user.getActivationCode();
        context.setVariable("url",url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(),"激活账号",content);
        return map;
    }

    /**
     * 激活用户
     * **/
    public int activation(int userId,String code){
        User user = userMapper.selectByUserId(userId);
        if(user.getStatus() == 1){
            return ACTIVATION_REPEAT;
        }else if(user.getActivationCode().equals(code)){
            userMapper.updateStatus(userId,1);
            //删除缓存
            clearCache(userId);
            return ACTIVATION_SUCCESS;
        }else {
            return ACTIVATION_FAILURE;
        }
    }

    /**
     * 登录
     * **/
    public Map<String,Object> login(String username,String password,int expiredSeconds){
        Map<String,Object> map=new HashMap<>();

        //空值处理
        if(StringUtils.isBlank(username)){
            map.put("usernameMsg","账号不能为空！");
            return map;
        }
        //空值处理
        if(StringUtils.isBlank(password)){
            map.put("passwordMsg","密码不能为空！");
            return map;
        }
        //验证账号
        User user = userMapper.selectByName(username);
        if(user == null){
            map.put("usernameMsg","该账号不存在！");
            return map;
        }

        //验证是否激活
        if(user.getStatus() == 0){
            map.put("usernameMsg","该账号未激活！");
            return map;
        }

        //验证密码
        password= CommunityUtil.md5(password + user.getSalt());
        if(!user.getPassword().equals(password)){
            map.put("passwordMsg","密码不正确！");
            return map;
        }

        //生成登录凭证
        LoginTicket loginTicket=new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis()+expiredSeconds*1000));
        //loginTicketMapper.insertLoginTicket(loginTicket);

        //将登录凭证loginTicket存入redis
        String redisKey = RedisKeyUtil.getTicketKey(loginTicket.getTicket());
        redisTemplate.opsForValue().set(redisKey,loginTicket);

        map.put("ticket",loginTicket.getTicket());

        return map;
    }

    /**退出登录**/
    public void logout(String ticket){
        //loginTicketMapper.updateStatus(ticket,1);
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        //先查出来，修改status，在存回去
        LoginTicket loginTicket = (LoginTicket)redisTemplate.opsForValue().get(redisKey);
        loginTicket.setStatus(1);
        redisTemplate.opsForValue().set(redisKey,loginTicket);
    }


    //查询凭证
    public LoginTicket findLoginTicket(String ticket){

        //return loginTicketMapper.selectByTicket(ticket);
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        LoginTicket loginTicket = (LoginTicket)redisTemplate.opsForValue().get(redisKey);
        return loginTicket;
    }

    //修改头像
    public int updateHeader(int userId,String headUrl){
        //return userMapper.updateHeader(userId,headUrl);
        int rows = userMapper.updateHeader(userId, headUrl);
        clearCache(userId);
        return rows;
    }

    //修改密码
    public Map<String,Object> updatePassword(int userId,String oldPassword,String newPassword){
        Map<String,Object> map=new HashMap<>();
        //空值处理
        if(StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)){
            map.put("passwordMsg","密码不能为空！");
            return map;
        }

        if(oldPassword.equals(newPassword)){
            map.put("passwordMsg","原密码不可与新密码一致！");
            return map;
        }

        User user = userMapper.selectByUserId(userId);
        String password=CommunityUtil.md5(oldPassword+user.getSalt());
        if(!password.equals(user.getPassword())){
            map.put("passwordMsg","原密码不正确！");
            return map;
        }

        newPassword=CommunityUtil.md5(newPassword+user.getSalt());

        userMapper.updatePassword(newPassword, userId);

        return map;
    }



    //根据用户名查询用户
    public User findUserByName(String username){
        return userMapper.selectByName(username);
    }


    //1.优先从缓存中获取用户
    private User getCache(int userId){
        String redisKey = RedisKeyUtil.getUserKey(userId);
        return (User) redisTemplate.opsForValue().get(redisKey);
    }

    //2.取不到时初始化缓存数据
    private User initCache(int userId){
        User user = userMapper.selectByUserId(userId);
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.opsForValue().set(redisKey,user,3600, TimeUnit.SECONDS); //1个小时失效
        return user;
    }

    //3.用户数据变更时清楚缓存数据
    private void clearCache(int userId){
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.delete(redisKey);
    }



    public List<User> findUsersBySelective(String title){
        return userMapper.selectUsers(title);
    }

}
