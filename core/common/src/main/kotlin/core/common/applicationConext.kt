package core.common

import android.annotation.SuppressLint
import android.content.Context

var commonApplicationContext: Context? = null

@SuppressLint("StaticFieldLeak")
val checkedContext = commonApplicationContext!!