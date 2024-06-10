package org.jxxy.debug

import android.content.Context
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class PythonThread(val context: Context):Runnable {
    override fun run() {
        if (!Python.isStarted()){
            Python.start(AndroidPlatform(context))
        }
    }
}