package com.xdsjs.droidhookproject;

import android.app.Application;
import android.util.Log;

import com.xdsjs.droidhook.DroidHookManager;
import com.xdsjs.droidhook.handler.HookedMethodHandler;

import java.lang.reflect.Method;

/**
 * 作者: hzsongjinsheng on 2016-03-29 14:17
 * 邮箱: hzsongjinsheng@corp.netease.com
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            DroidHookManager.getInstance().installIActivityManagerHook(null, "startActivity", new HookedMethodHandler(this) {
                @Override
                protected boolean beforeInvoke(Object receiver, Method method, Object[] args) throws Throwable {
                    Log.e("-------------->", "before " + method.getName() + " invoke!");
                    return super.beforeInvoke(receiver, method, args);
                }

                @Override
                protected void afterInvoke(Object receiver, Method method, Object[] args, Object invokeResult) throws Throwable {
                    Log.e("-------------->", "after " + method.getName() + " invoke!");
                    super.afterInvoke(receiver, method, args, invokeResult);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        DroidHookManager.getInstance().setHookEnable(true);
    }
}
