package com.jfinal.rest;

import com.jfinal.render.IMainRenderFactory;
import com.jfinal.render.JsonRender;
import com.jfinal.render.Render;

/**
 * Created by peak on 2015/1/27.
 */
public class JsonRenderFactory implements IMainRenderFactory {
    @Override
    public Render getRender(String view) {
        return new JsonRender();
    }

    @Override
    public String getViewExtension() {
        return null;
    }
}
