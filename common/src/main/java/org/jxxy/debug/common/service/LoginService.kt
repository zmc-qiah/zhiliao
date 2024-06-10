package org.jxxy.debug.common.service

import android.content.Context
import org.jxxy.debug.corekit.common.CommonServiceManager

interface LoginService {
    fun gotoLoginPage(context: Context)
    fun gotoRegister(context: Context)
    fun isLogin(): Boolean
}

fun isLogin(): Boolean = CommonServiceManager.service<LoginService>()?.isLogin() ?: false
fun goLogin(context: Context) {
    CommonServiceManager.service<LoginService>()?.gotoLoginPage(context)
}
inline fun loginCheck(context: Context,ifLogin: () -> Unit){
    if (isLogin()){
        ifLogin()
    }else{
        goLogin(context)
    }
}
