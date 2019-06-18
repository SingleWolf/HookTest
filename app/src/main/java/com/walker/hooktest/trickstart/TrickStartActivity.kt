package com.walker.hooktest.trickstart

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.walker.hooktest.R


class TrickStartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trick_start)
        initHook()
    }

    private fun initHook() {
        val instrumentation = ReflexUtils.getFieldObject(Activity::class.java, this@TrickStartActivity, "mInstrumentation") as Instrumentation
        val instrumentation1 = HookInstrumentation(instrumentation)
        ReflexUtils.setFieldObject(Activity::class.java, this, "mInstrumentation", instrumentation1)

        AmsHookHelperUtils.hookAm()
        AmsHookHelperUtils.hookActivityThread()
    }

    public fun onTrickStart(v: View) {
        Intent(this, BlackActivity::class.java).let {
            it.putExtra(AmsHookHelperUtils.KEY_IS_TRICK,true)
            startActivity(it)
        }
    }
}