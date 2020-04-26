package com.zqy.sharecommunity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.concurrent.TimeUnit;

/**
 * @Author zqy
 * @Date 2020/03/20
 */
@SpringBootTest
public class RedisTests {
    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void testStrings(){
        String redisKey="test:count";
        redisTemplate.opsForValue().set(redisKey,1);


        System.out.println(redisTemplate.opsForValue().get(redisKey));
        System.out.println(redisTemplate.opsForValue().increment(redisKey));
        System.out.println(redisTemplate.opsForValue().decrement(redisKey));
    }

    @Test
    public void testHashes(){
        String redisKey="test:user";

        redisTemplate.opsForHash().put(redisKey,"id",1);
        redisTemplate.opsForHash().put(redisKey,"username","zhangsan");
        System.out.println(redisTemplate.opsForHash().get(redisKey,"id"));
        System.out.println(redisTemplate.opsForHash().get(redisKey,"username"));

    }

    @Test
    public void testLists(){
        String redisKey="test:ids";

        redisTemplate.opsForList().leftPush(redisKey,101);  //从左边存数据
        redisTemplate.opsForList().leftPush(redisKey,102);
        redisTemplate.opsForList().leftPush(redisKey,103);

        System.out.println(redisTemplate.opsForList().size(redisKey));
        System.out.println(redisTemplate.opsForList().index(redisKey,0)); //获取下标0的数据
        System.out.println(redisTemplate.opsForList().range(redisKey,0,2)); //获取0-2范围的数据

        //出队
        System.out.println(redisTemplate.opsForList().leftPop(redisKey)); //从左边弹出数据
        System.out.println(redisTemplate.opsForList().leftPop(redisKey)); //从左边弹出数据
        System.out.println(redisTemplate.opsForList().leftPop(redisKey)); //从左边弹出数据
    }

    @Test
    public void testSets(){
        String redisKey="test:teachers";

        redisTemplate.opsForSet().add(redisKey,"刘备","关羽","张飞","赵云","诸葛亮");

        System.out.println(redisTemplate.opsForSet().size(redisKey));
        System.out.println(redisTemplate.opsForSet().pop(redisKey)); //随机弹出一个数据
        System.out.println(redisTemplate.opsForSet().members(redisKey)); // 统计数据
    }

    @Test
    public void testSortedSets() {
        String redisKey = "test:students";

        redisTemplate.opsForZSet().add(redisKey, "唐僧", 80);
        redisTemplate.opsForZSet().add(redisKey, "悟空", 90);
        redisTemplate.opsForZSet().add(redisKey, "八戒", 50);
        redisTemplate.opsForZSet().add(redisKey, "沙僧", 70);
        redisTemplate.opsForZSet().add(redisKey, "白龙马", 60);

        System.out.println(redisTemplate.opsForZSet().zCard(redisKey));
        System.out.println(redisTemplate.opsForZSet().score(redisKey, "八戒"));  //统计key的分数
        System.out.println(redisTemplate.opsForZSet().reverseRank(redisKey, "八戒"));  //获取key的排名
        System.out.println(redisTemplate.opsForZSet().reverseRange(redisKey, 0, 2)); //获取0-2的范围的数据
    }


    //公共命令
    @Test
    public void testKeys() {
        redisTemplate.delete("test:count");
        redisTemplate.delete("test:teachers");
        redisTemplate.delete("test:tx");
        redisTemplate.delete("test:ids");


        //System.out.println(redisTemplate.hasKey("test:user"));

        redisTemplate.expire("followee:161:3", 1, TimeUnit.SECONDS);
    }

    // 多次访问同一个key  bound： 绑定
    @Test
    public void testBoundOperations() {
        String redisKey = "test:count";
        BoundValueOperations operations = redisTemplate.boundValueOps(redisKey);  //绑定key
        operations.increment();
        operations.increment();
        operations.increment();
        operations.increment();
        operations.increment();
        System.out.println(operations.get());
    }

    // 编程式事务    不可在事务中间查询数据，数据要在事务提交后查询
    @Test
    public void testTransactional() {
        Object obj = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String redisKey = "test:tx";

                operations.multi();

                operations.opsForSet().add(redisKey, "zhangsan");
                operations.opsForSet().add(redisKey, "lisi");
                operations.opsForSet().add(redisKey, "wangwu");

                System.out.println(operations.opsForSet().members(redisKey));

                return operations.exec();
            }
        });
        System.out.println(obj);
    }

}
