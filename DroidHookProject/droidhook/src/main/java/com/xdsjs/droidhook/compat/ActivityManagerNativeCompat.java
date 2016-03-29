
package com.xdsjs.droidhook.compat;


import com.xdsjs.droidhook.reflect.MethodUtils;

import java.lang.reflect.InvocationTargetException;

public class ActivityManagerNativeCompat {

    private static Class sClass;

    public static Class Class() throws ClassNotFoundException {
        if (sClass == null) {
            sClass = Class.forName("android.app.ActivityManagerNative");
        }
        return sClass;
    }

    public static Object getDefault() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return MethodUtils.invokeStaticMethod(Class(), "getDefault");
    }
}
