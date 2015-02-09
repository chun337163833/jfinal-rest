package com.jfinal.rest;

import java.lang.annotation.*;

/**
 * 用来标识RESTful controller路径的注解
 * Created by peak on 2015/2/9.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface API {
    String value();
}
