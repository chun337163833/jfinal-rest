package com.jfinal.rest;

import com.jfinal.config.Routes;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by peak on 2015/1/23.
 */
class RestRoutes {

    private String visitPath;
    private Routes routes;
    private Set<RestKey> restKeySet = new HashSet<RestKey>();

    public RestRoutes(String visitPath, Routes routes) {
        if (!visitPath.startsWith("/")) {
            visitPath = "/" + visitPath;
        }
        if (visitPath.endsWith("/")) {
            visitPath = visitPath.substring(0, visitPath.length() - 2);
        }
        this.visitPath = visitPath;
        this.routes = routes;
    }

    public String getVisitPath() {
        return visitPath;
    }

    public void addRoute(String restKey, Class<? extends Controller> controllerClass) {
        if (!restKey.startsWith("/")) {
            restKey = "/" + restKey;
        }
        routes.add(visitPath + restKey, controllerClass);
        RestKey key = new RestKey(restKey);
        if (restKeySet.contains(key)) {
            throw new RuntimeException("restKey重复：" + key);
        }
        restKeySet.add(key);
    }


    protected String match(String target, HttpServletRequest request) {
        String key = target;
        if (visitPath != null) {
            target = target.substring(key.length());
        }
        for (RestKey restKey : restKeySet) {
            String t = restKey.match(target, request);
            if (t != null) {
                return t;
            }
        }
        return null;
    }


}
