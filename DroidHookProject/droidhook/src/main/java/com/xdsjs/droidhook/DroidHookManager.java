package com.xdsjs.droidhook;

import com.xdsjs.droidhook.handler.HookedMethodHandler;
import com.xdsjs.droidhook.hook.BaseHook;
import com.xdsjs.droidhook.hook.IActivityManagerHook;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: hzsongjinsheng on 2016-03-29 10:51
 * 邮箱: hzsongjinsheng@corp.netease.com
 */
public class DroidHookManager {

    private static final String TAG = DroidHookManager.class.getSimpleName();

    private boolean hookEnable = false;//默认关掉hook功能

    private List<BaseHook> hooks = new ArrayList<>();//缓存已经安装的hook

    private static final class HoldClass {
        private static DroidHookManager singleInstance = new DroidHookManager();
    }

    public static DroidHookManager getInstance() {
        return HoldClass.singleInstance;
    }

    public final void setHookEnable(boolean hookEnable) {
        this.hookEnable = hookEnable;
        for (BaseHook baseHook : hooks) {
            baseHook.setmEnable(hookEnable);
        }
    }

    public boolean getHookEnable() {
        return this.hookEnable;
    }

    public void installHook(BaseHook baseHook, ClassLoader cl) throws Throwable {
        baseHook.onInstall(cl);
        hooks.add(baseHook);
    }

    public void installIActivityManagerHook(ClassLoader cl, String methodName, HookedMethodHandler hookMethodHandler) throws Throwable {
        installHook(new IActivityManagerHook(methodName, hookMethodHandler), cl);
    }

}
