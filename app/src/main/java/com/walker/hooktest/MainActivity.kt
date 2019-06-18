package com.walker.hooktest

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.walker.hooktest.trickstart.TrickStartActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    public fun onClick(v: View) = Intent(this, TrickStartActivity::class.java).let { startActivity(it) }
}
