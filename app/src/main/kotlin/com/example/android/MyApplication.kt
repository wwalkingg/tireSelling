package com.example.android

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import coil.Coil
import coil.ImageLoader
import coil.util.Logger
import okhttp3.OkHttpClient
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}

