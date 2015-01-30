package com.jfinal.rest;

import com.jfinal.core.Controller;

/**
 * Created by peak on 2015/1/23.
 */
public abstract class RestController extends Controller {

    public abstract void get();

    public abstract void post();

    public abstract void put();

    public abstract void patch();

    public abstract void delete();
}
