package com.walker.hooktest.trickstart;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * @Author Walker
 * @Date 2019/6/12 下午3:38
 * @Summary 继承改写Instrumentation，以达到hook目的
 */
public class HookInstrumentation extends Instrumentation {

    private Instrumentation mInstrumentation;

    public HookInstrumentation(Instrumentation instrumentation) {
        mInstrumentation = instrumentation;
    }

    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target, Intent intent, int requestCode, Bundle options) {
        Log.d(AmsHookHelperUtils.TAG, "啦啦啦我是从HookInstrumentation处hook进来的～");
        Class[] classes = {Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class, Bundle.class};
        Object[] objects = {who, contextThread, token, target, intent, requestCode, options};
        return (ActivityResult) ReflexUtils.invokeInstanceMethod(mInstrumentation, "execStartActivity", classes, objects);
    }
}
