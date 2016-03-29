package com.xdsjs.droidhook.hook;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 作者: hzsongjinsheng on 2016-03-29 11:25
 * 邮箱: hzsongjinsheng@corp.netease.com
 */
public abstract class proxyHook extends BaseHook implements InvocationHandler {

    protected Object mOldObj;//被代理的对象

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (!isEnable())
            return method.invoke(mOldObj, args);

        if (hookedMethodHandler != null && method.getName() == methodName) {
            return hookedMethodHandler.doHookInner(mOldObj, method, args);
        }
        return method.invoke(mOldObj, args);
    }

    public void setmOldObj(Object oldObj) {
        this.mOldObj = oldObj;
    }
}
