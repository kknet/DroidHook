package com.xdsjs.droidhookproject;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
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
                    if (args != null && args.length > 0) {
                        for (int i = 0; i < args.length; i++) {
                            if (args[i] != null && args[i] instanceof Intent) {
                                //劫持Intent并指向ThirdActivity
                                args[i] = new Intent().setClass(getApplicationContext(), ThirdActivity.class);
                            }
                        }
                    }
                    return super.beforeInvoke(receiver, method, args);
                }

                @Override
                protected void afterInvoke(Object receiver, Method method, Object[] args, Object invokeResult) throws Throwable {
                    super.afterInvoke(receiver, method, args, invokeResult);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        DroidHookManager.getInstance().setHookEnable(true);
    }
}
