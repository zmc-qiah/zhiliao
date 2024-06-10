package com.debug.myapplication.widget.read3

import android.util.Log

object DLog {
    fun log(rules: String?, vararg args: Any?) {
        Log.i("DLog:", String.format(rules!!, *args))
    }
}
