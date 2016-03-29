package com.xdsjs.droidhook.hook;

import com.xdsjs.droidhook.handler.HookedMethodHandler;

/**
 * 作者: hzsongjinsheng on 2016-03-29 11:02
 * 邮箱: hzsongjinsheng@corp.netease.com
 */
public abstract class BaseHook {


    private boolean mEnable = false;
    protected HookedMethodHandler hookedMethodHandler;
    protected String methodName;

    public void setmEnable(boolean enable) {
        this.mEnable = enable;
    }

    public boolean isEnable() {
        return mEnable;
    }

    public abstract void onInstall(ClassLoader classLoader) throws Throwable;
}
