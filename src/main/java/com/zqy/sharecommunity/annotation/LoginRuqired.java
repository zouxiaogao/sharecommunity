package com.zqy.sharecommunity.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author zqy
 * @Date 2020/03/15
 */

@Target(ElementType.METHOD)  //作用在方法上
@Retention(RetentionPolicy.RUNTIME) // 运行有效
public @interface LoginRuqired {
}
