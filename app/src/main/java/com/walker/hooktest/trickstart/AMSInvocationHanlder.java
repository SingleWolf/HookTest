package com.walker.hooktest.trickstart;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AMSInvocationHanlder implements InvocationHandler {
    private String actionName = "startActivity";

    private Object target;

    public AMSInvocationHanlder(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.getName().equals(actionName)) {
            Log.d(AmsHookHelperUtils.TAG, "啦啦啦我是hook AMS进来的");
            Intent intent;
            int index = 0;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }
            intent = (Intent) args[index];
            boolean isTrick = intent.getBooleanExtra(AmsHookHelperUtils.KEY_IS_TRICK, false);
            if (isTrick) {
                String packageName = intent.getComponent().getPackageName();
                Intent newIntent = new Intent();
                ComponentName componentName = new ComponentName(packageName, WhiteActivity.class.getName());
                newIntent.setComponent(componentName);
                //将目标Intent藏于newIntent
                newIntent.putExtra(AmsHookHelperUtils.KEY_TRUE_INTENT, intent);
                args[index] = newIntent;
            }

            return method.invoke(target, args);
        }

        return method.invoke(target, args);
    }
}
