package com.xdsjs.droidhook.handle;

import com.xdsjs.droidhook.handler.HookedMethodHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 作者: hzsongjinsheng on 2016-03-29 11:20
 * 邮箱: hzsongjinsheng@corp.netease.com
 */
public abstract class BaseHookHandle {


    protected Map<String, HookedMethodHandler> sHookedMethodHandlers = new HashMap<String, HookedMethodHandler>(5);

    public BaseHookHandle() {
        init();
    }

    protected abstract void init();

    public Set<String> getHookedMethodNames() {
        return sHookedMethodHandlers.keySet();
    }

    public HookedMethodHandler getHookedMethodHandler(Method method) {
        if (method != null) {
            return sHookedMethodHandlers.get(method.getName());
        } else {
            return null;
        }
    }
}
