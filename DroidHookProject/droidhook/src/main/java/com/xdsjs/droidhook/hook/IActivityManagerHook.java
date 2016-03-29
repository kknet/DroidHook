package com.xdsjs.droidhook.hook;

import android.util.AndroidRuntimeException;
import android.util.Log;

import com.xdsjs.droidhook.compat.ActivityManagerNativeCompat;
import com.xdsjs.droidhook.compat.IActivityManagerCompat;
import com.xdsjs.droidhook.compat.SingletonCompat;
import com.xdsjs.droidhook.handler.HookedMethodHandler;
import com.xdsjs.droidhook.reflect.FieldUtils;
import com.xdsjs.droidhook.utils.Utils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 作者: hzsongjinsheng on 2016-03-29 11:34
 * 邮箱: hzsongjinsheng@corp.netease.com
 */
public class IActivityManagerHook extends proxyHook {

    private static final String TAG = IActivityManagerHook.class.getSimpleName();

    public IActivityManagerHook(String methodName, HookedMethodHandler hookedMethodHandler) {
        this.methodName = methodName;
        this.hookedMethodHandler = hookedMethodHandler;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!Utils.isClassContainedMethod(IActivityManagerCompat.Class(), methodName)) {
            Log.e("--------->", "not found menthod-->" + methodName + "<--in " + IActivityManagerCompat.Class().getName());
            throw new RuntimeException("not found menthod-->" + methodName + "<--in " + IActivityManagerCompat.Class().getName());
        }
        return super.invoke(proxy, method, args);
    }

    @Override
    public void onInstall(ClassLoader classLoader) throws Throwable {
        Class cls = ActivityManagerNativeCompat.Class();

        Object obj = FieldUtils.readStaticField(cls, "gDefault");
        if (obj == null) {
            ActivityManagerNativeCompat.getDefault();
            obj = FieldUtils.readStaticField(cls, "gDefault");
        }
        if (IActivityManagerCompat.isIActivityManager(obj)) {
            setmOldObj(obj);
            Class<?> objClass = mOldObj.getClass();
            List<Class<?>> interfaces = Utils.getAllInterfaces(objClass);
            Class[] ifs = interfaces != null && interfaces.size() > 0 ? interfaces.toArray(new Class[interfaces.size()]) : new Class[0];
            Object proxiedActivityManager = MyProxy.newProxyInstance(objClass.getClassLoader(), ifs, this);
            FieldUtils.writeStaticField(cls, "gDefault", proxiedActivityManager);
            Log.i(TAG, "Install ActivityManager BaseHook 1 old=%s,new=%s");
        } else if (SingletonCompat.isSingleton(obj)) {
            Object obj1 = FieldUtils.readField(obj, "mInstance");
            if (obj1 == null) {
                SingletonCompat.get(obj);
                obj1 = FieldUtils.readField(obj, "mInstance");
            }
            setmOldObj(obj1);
            List<Class<?>> interfaces = Utils.getAllInterfaces(mOldObj.getClass());
            Class[] ifs = interfaces != null && interfaces.size() > 0 ? interfaces.toArray(new Class[interfaces.size()]) : new Class[0];
            final Object object = MyProxy.newProxyInstance(mOldObj.getClass().getClassLoader(), ifs, IActivityManagerHook.this);
            Object iam1 = ActivityManagerNativeCompat.getDefault();

            //这里先写一次，防止后面找不到Singleton类导致的挂钩子失败的问题。
            FieldUtils.writeField(obj, "mInstance", object);

            //这里使用方式1，如果成功的话，会导致上面的写操作被覆盖。
            FieldUtils.writeStaticField(cls, "gDefault", new android.util.Singleton<Object>() {
                @Override
                protected Object create() {
                    Log.e(TAG, "Install ActivityManager 3 BaseHook  old=%s,new=%s");
                    return object;
                }
            });

            Log.i(TAG, "Install ActivityManager BaseHook 2 old=%s,new=%s");
            Object iam2 = ActivityManagerNativeCompat.getDefault();
            // 方式2
            if (iam1 == iam2) {
                //这段代码是废的，没啥用，写这里只是不想改而已。
                FieldUtils.writeField(obj, "mInstance", object);
            }
        } else {
            throw new AndroidRuntimeException("Can not install IActivityManagerNative hook");
        }
    }


}
