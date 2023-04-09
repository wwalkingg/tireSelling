package com.example.android

import android.app.Application
import core.common.commonApplicationContext

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        commonApplicationContext = this
    }
}