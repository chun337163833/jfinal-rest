package com.jfinal.rest;

import com.jfinal.config.Handlers;
import com.jfinal.config.Routes;

import java.util.ArrayList;
import java.util.List;

/**
 * RESTful工具类，两个功能：创建路由和Handler
 * Created by peak on 2015/1/30.
 */
public class RestKit {

    private static final List<RestRoutes> routesList = new ArrayList<RestRoutes>();

    /**
     * 创建路由
     *
     * @param visitPath 访问路径，如/v1，/v2
     * @param routes    路由，jfinal自带的路由
     * @param pack      包名，将会扫描该下带有@Api注解的controller
     */
    public static void buildRoutes(String visitPath, Routes routes, String pack) {
        RestRoutes restRoutes = new RestRoutes(visitPath, routes);
        //TODO 扫描包下的controller
    }

    /**
     * 构建handler
     *
     * @param handlers
     */
    public static void buildHandler(Handlers handlers) {
        for (RestRoutes routes : routesList) {
            handlers.add(new RestHandler(routes));
        }
    }


}
