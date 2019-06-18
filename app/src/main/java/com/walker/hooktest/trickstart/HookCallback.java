package com.walker.hooktest.trickstart;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class HookCallback implements Handler.Callback {
    private Handler mBase;

    public HookCallback(Handler base) {
        mBase = base;
    }

    @Override
    public boolean handleMessage(Message msg) {
        Log.d(AmsHookHelperUtils.TAG, "msg is " + msg.toString());
        switch (msg.what) {
            case 100:
                //SDK>=28  :EXECUTE_TRANSACTION
            case 159:
                handleLaunchActivity(msg);
                break;
            default:
                break;

        }
        mBase.handleMessage(msg);
        return true;
    }

    private void handleLaunchActivity(Message msg) {
        Object obj = msg.obj;
        Log.d(AmsHookHelperUtils.TAG, "handleLaunchActivity->msg.obj is " + obj.getClass().getName());
        Intent intent = (Intent) ReflexUtils.getFieldObject(obj.getClass(), obj, "intent");
        if (intent != null) {
            Intent targetIntent = intent.getParcelableExtra(AmsHookHelperUtils.KEY_TRUE_INTENT);
            if (targetIntent != null) {
                intent.setComponent(targetIntent.getComponent());
                Log.d(AmsHookHelperUtils.TAG, "targetIntent is " + targetIntent.getComponent());
                Log.d(AmsHookHelperUtils.TAG, "intent is " + intent.getComponent());
            } else {
                Log.d(AmsHookHelperUtils.TAG, "targetIntent is null");
            }
        }
    }
}
