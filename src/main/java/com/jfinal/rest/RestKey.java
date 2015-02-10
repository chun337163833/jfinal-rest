package com.jfinal.rest;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * rest action key
 * Created by peak on 2015/1/23.
 */
public class RestKey {
    private String origin;
    private List<Part> parts;

    public RestKey(String origin) {
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
        if (target.startsWith("/")) {
            target = target.substring(1);
        }
        String[] arr = target.split("/");

        if ("GET".equals(method)) {
            return matchGet(arr, request);
        } else if ("POST".equals(method)) {
            return matchPost(arr, request);
        } else if ("PUT".equals(method)) {
            return matchPut(arr, request);
        } else if ("PATCH".equals(method)) {
            return matchPatch(arr, request);
        } else if ("DELETE".equals(method)) {
            return matchDelete(arr, request);
        } else {
            return null;
        }

    }

    private String matchGet(String[] arr, HttpServletRequest request) {
        Map<String, String> paras = new HashMap<String, String>();
        //GET /tickets/:ticketId/messages
        if (arr.length == parts.size()) {
            for (int i = 0; i < arr.length; i++) {
                String str = arr[i];
                Part part = parts.get(i);
                if (part.str != null && !part.str.equals(str)) {
                    return null;
                }
                paras.put(part.variable, str);
            }

            for (Map.Entry<String, String> entry : paras.entrySet()) {
                request.setAttribute(entry.getKey(), entry.getValue());
            }
            return origin + "/get";
        }
        if (arr.length != (parts.size() + 1)) {
            return null;
        }
        //GET /tickets/:ticketId/messages/:messagesId
        for (int i = 0; i < paras.size(); i++) {
            String str = arr[i];
            Part part = parts.get(i);
            if (part.str != null && !part.str.equals(str)) {
                return null;
            }
            paras.put(part.variable, str);
        }
        for (Map.Entry<String, String> entry : paras.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        return origin + "/get/" + arr[arr.length - 1];
    }

    private String matchPost(String[] arr, HttpServletRequest request) {
        Map<String, String> paras = new HashMap<String, String>();
        if (arr.length != parts.size()) {
            return null;
        }
        for (int i = 0; i < paras.size(); i++) {
            String str = arr[i];
            Part part = parts.get(i);
            if (part.str != null && !part.str.equals(str)) {
                return null;
            }
            paras.put(part.variable, str);
        }
        for (Map.Entry<String, String> entry : paras.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        return origin + "/post";
    }

    private String matchPut(String[] arr, HttpServletRequest request) {
        if (arr.length != (parts.size() + 1)) {
            return null;
        }
        Map<String, String> paras = new HashMap<String, String>();
        for (int i = 0; i < paras.size(); i++) {
            String str = arr[i];
            Part part = parts.get(i);
            if (part.str != null && !part.str.equals(str)) {
                return null;
            }
            paras.put(part.variable, str);
        }
        for (Map.Entry<String, String> entry : paras.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        return origin + "/put/" + arr[arr.length - 1];
    }

    private String matchPatch(String[] arr, HttpServletRequest request) {
        if (arr.length != (parts.size() + 1)) {
            return null;
        }
        Map<String, String> paras = new HashMap<String, String>();
        for (int i = 0; i < paras.size(); i++) {
            String str = arr[i];
            Part part = parts.get(i);
            if (part.str != null && !part.str.equals(str)) {
                return null;
            }
            paras.put(part.variable, str);
        }
        for (Map.Entry<String, String> entry : paras.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        return origin + "/patch/" + arr[arr.length - 1];
    }

    private String matchDelete(String[] arr, HttpServletRequest request) {
        if (arr.length != (parts.size() + 1)) {
            return null;
        }
        Map<String, String> paras = new HashMap<String, String>();
        for (int i = 0; i < paras.size(); i++) {
            String str = arr[i];
            Part part = parts.get(i);
            if (part.str != null && !part.str.equals(str)) {
                return null;
            }
            paras.put(part.variable, str);
        }
        for (Map.Entry<String, String> entry : paras.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        return origin + "/delete/" + arr[arr.length - 1];
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
