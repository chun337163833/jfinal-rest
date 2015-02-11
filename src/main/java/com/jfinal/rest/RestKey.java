package com.jfinal.rest;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * rest action key
 * Created by peak on 2015/1/23.
 */
class RestKey {
    private String origin;
    private List<Part> parts;

    RestKey(String origin) {
        this.origin = origin;
        if (origin.startsWith("/")) {
            origin = origin.substring(1);
        }
        String[] arr = origin.split("/");
        parts = new ArrayList<Part>();
        for (String str : arr) {
            Part part = new Part();
            if (str.startsWith(":")) {
                part.variable = str.substring(1);
            } else {
                part.str = str;
            }
            parts.add(part);
        }
    }

    /**
     * 匹配
     *
     * @param target
     * @param request
     * @return
     */
    String match(String target, HttpServletRequest request) {
        String method = request.getMethod().toUpperCase();
        //将target按斜线拆分成数组，用于匹配
        if (target.startsWith("/")) {
            target = target.substring(1);
        }
        String[] arr = target.split("/");
        //url结尾的参数，在controller里可以通过getPara()获得
        String para = null;
        if (arr.length == parts.size() + 1) {
            para = arr[arr.length - 1];
        } else if (arr.length != parts.size()) {
            return null;
        }
        //逐个部分进行比较
        Map<String, String> paras = new HashMap<String, String>();
        for (int i = 0; i < parts.size(); i++) {
            String str = arr[i];
            Part part = parts.get(i);
            if (part.str != null) {
                if (!part.str.equals(str)) {
                    return null;
                }
                continue;
            }
            paras.put(part.variable, str);
        }
        //在request里放入rest参数
        for (Map.Entry<String, String> entry : paras.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        //根据请求方法进行判断
        if ("GET".equals(method)) {
            if (para == null) {
                return origin + "/get";
            } else {
                return origin + "/get/" + para;
            }
        } else if ("POST".equals(method)) {
            if (para != null) {
                return null;
            }
            return origin + "/post";
        } else if ("PUT".equals(method)) {
            if (para == null) {
                return null;
            }
            return origin + "/put/" + para;
        } else if ("PATCH".equals(method)) {
            if (para == null) {
                return null;
            }
            return origin + "/patch/" + para;
        } else if ("DELETE".equals(method)) {
            if (para == null) {
                return null;
            }
            return origin + "/delete/" + para;
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RestKey)) {
            return false;
        }
        RestKey other = (RestKey) obj;
        if (parts.size() != other.parts.size()) {
            return false;
        }
        for (int i = 0; i < parts.size(); i++) {
            if (!parts.get(i).equals(other.parts.get(i))) {
                return false;
            }
        }
        return true;

    }

    private class Part {
        String str;
        String variable;

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Part)) {
                return false;
            }
            Part other = (Part) obj;
            return variable != null && other.variable != null || str.equals(other.str);
        }
    }
}
