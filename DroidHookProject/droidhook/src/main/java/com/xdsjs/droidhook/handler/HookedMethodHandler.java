
package com.xdsjs.droidhook.handler;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;

public class HookedMethodHandler {

    private static final String TAG = HookedMethodHandler.class.getSimpleName();
    protected final Context mHostContext;

    private Object mFakedResult = null;
    private boolean mUseFakedResult = false;

    public HookedMethodHandler(Context hostContext) {
        this.mHostContext = hostContext;
    }


    public synchronized Object doHookInner(Object receiver, Method method, Object[] args) throws Throwable {
        long b = System.currentTimeMillis();
        try {
            mUseFakedResult = false;
            mFakedResult = null;
            boolean suc = beforeInvoke(receiver, method, args);
            Object invokeResult = null;
            if (!suc) {
                invokeResult = method.invoke(receiver, args);
            }
            afterInvoke(receiver, method, args, invokeResult);
            if (mUseFakedResult) {
                return mFakedResult;
            } else {
                return invokeResult;
            }
        } finally {
            long time = System.currentTimeMillis() - b;
            if (time > 5) {
                Log.i(TAG, "doHookInner method(%s.%s) cost %s ms");
            }
        }
    }

    /**
     * 在某个方法被调用之前执行，如果返回true，则不执行原始的方法，否则执行原始方法
     */
    protected boolean beforeInvoke(Object receiver, Method method, Object[] args) throws Throwable {
        return false;
    }

    protected void afterInvoke(Object receiver, Method method, Object[] args, Object invokeResult) throws Throwable {
    }
}
