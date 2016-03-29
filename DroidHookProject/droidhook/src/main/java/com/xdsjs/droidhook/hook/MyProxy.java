
package com.xdsjs.droidhook.hook;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.SocketException;

/**
 * Created by Andy Zhang(zhangyong232@gmail.com)ClassUtils on 2015/3/25.
 */
public class MyProxy {

    public static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces,
                                          InvocationHandler invocationHandler) {
        return Proxy.newProxyInstance(loader, interfaces,
                invocationHandler);
    }

    /**
     * 判断某个异常是否已经在某个方法上声明了。
     */
    public static boolean isMethodDeclaredThrowable(Method method, Throwable e) {
        if (e instanceof RuntimeException) {
            return true;
        }

        if (method == null || e == null) {
            return false;
        }

        Class[] es = method.getExceptionTypes();
        if (es == null && es.length <= 0) {
            return false;
        }

        try {
            String methodName = method.getName();
            boolean va = "accept".equals(methodName) || "sendto".equals(methodName);
            if (e instanceof SocketException && va && method.getDeclaringClass().getName().indexOf("libcore") >= 0) {
                return true;
            }
        } catch (Throwable e1) {
            //DO NOTHING
        }

        for (Class aClass : es) {
            if (aClass.isInstance(e) || aClass.isAssignableFrom(e.getClass())) {
                return true;
            }
        }
        return false;
    }
}
