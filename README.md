## DdroidHook
---

> 基于JDK动态代理实现的Android System API HOOK 工具

## 特点
---
- 劫持、加强系统API，动态注入代码
- 轻松实现AOP编程

## 待完成

- 完善对System API的支持，目前只支持IActivityManager
- 可以hook自己项目的类
- 打印日志、统计函数执行时间等功能

## 使用方法

在想要hook的位置调用DroidHookManager.getInstance().installI***Hook(ClassLoader cl, String methodName, HookedMethodHandler hookMethodHandler)。

## 示例
我们知道IActivityManager里面定义了很多非常重要的方法，例如startActivity等。我们可以通过hook这一方法实现很多正常无法实现的功能。例如，可以劫持传入startActivity方法的Intent，将其重新指向另外的Activity。
```java
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
```
![](https://raw.githubusercontent.com/xdsjs/imageSource/master/55.gif)








