package com.redissonlock.lock.common.aop;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpLog {


    //备注
    String note() default "";
}

