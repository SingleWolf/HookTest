package com.walker.hooktest.trickstart;

import android.os.Build;
import android.os.Handler;
import android.util.Log;

import java.lang.reflect.Proxy;

public class AmsHookHelperUtils {

    public static final String TAG="HookHelper";

    public static final String KEY_IS_TRICK = "is_trick";

    public static final String KEY_TRUE_INTENT = "true_intent";

    public static void hookAmn() throws ClassNotFoundException {
        Object gDefault = ReflexUtils.getStaticFieldObject("android.app.ActivityManagerNative", "gDefault");
        Object mInstance = ReflexUtils.getFieldObject("android.util.Singleton", gDefault, "mInstance");

        Class<?> classInterface = Class.forName("android.app.IActivityManager");
        Object proxy = Proxy.newProxyInstance(classInterface.getClassLoader(),
                new Class<?>[]{classInterface}, new AMSInvocationHanlder(mInstance));
        ReflexUtils.setFieldObject("android.util.Singleton", gDefault, "mInstance", proxy);
    }

    public static void hookAms() throws ClassNotFoundException {
        Object gDefault = ReflexUtils.getStaticFieldObject("android.app.ActivityManager", "IActivityManagerSingleton");
        Object mInstance = ReflexUtils.getFieldObject("android.util.Singleton", gDefault, "mInstance");

        Class<?> classInterface = Class.forName("android.app.IActivityManager");
        Object proxy = Proxy.newProxyInstance(classInterface.getClassLoader(),
                new Class<?>[]{classInterface}, new AMSInvocationHanlder(mInstance));
        ReflexUtils.setFieldObject("android.util.Singleton", gDefault, "mInstance", proxy);
    }

    public static void hookAm() throws ClassNotFoundException {
        if (Build.VERSION.SDK_INT < 26) {
            hookAmn();
        } else {
            hookAms();
        }
    }

    public static void hookActivityThread() {
        Object currentActivityThread = ReflexUtils.getStaticFieldObject("android.app.ActivityThread", "sCurrentActivityThread");
        Log.d(TAG,"currentActivityThread is "+currentActivityThread.toString());
        Handler mH = (Handler) ReflexUtils.getFieldObject(currentActivityThread.getClass(),currentActivityThread, "mH");
        Log.d(TAG,"mH is "+mH.toString());
        ReflexUtils.setFieldObject(Handler.class, mH, "mCallback", new HookCallback(mH));
    }
}
