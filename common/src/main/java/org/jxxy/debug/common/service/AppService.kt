package org.jxxy.debug.common.service

import android.content.Context
import org.jxxy.debug.corekit.common.CommonServiceManager

interface AppService {
    fun goHome(context:Context)
    fun sendReq()
    fun goMainActivity(context: Context, selected: Int)
}

fun goHome(context:Context){
    CommonServiceManager.service<AppService>()?.goHome(context)
}

fun goMainActivity(context:Context, selected : Int){
    CommonServiceManager.service<AppService>()?.goMainActivity(context,selected)
}