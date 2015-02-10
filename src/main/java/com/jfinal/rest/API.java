package com.jfinal.rest;

import java.lang.annotation.*;

/**
 * 放在controller上，配置restful请求路径
 * Created by peak on 2015/2/9.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface API {
    String value();
}
