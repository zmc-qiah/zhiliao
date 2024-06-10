package org.jxxy.debug.resources.util

import android.content.Context
import org.jxxy.debug.common.service.LoginService
import org.jxxy.debug.corekit.common.BaseApplication
import org.jxxy.debug.corekit.common.CommonServiceManager

object MyUtil {
    val url = "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/%E9%BB%98%E8%AE%A4.jpg"
    fun isLogin(): Boolean = CommonServiceManager.service<LoginService>()?.isLogin() ?: false
    fun goLogin(context: Context) {
        CommonServiceManager.service<LoginService>()?.gotoLoginPage(context)
    }
    fun getHeight(): Int = BaseApplication.context().resources.displayMetrics.heightPixels
    fun getWidth(): Int = BaseApplication.context().resources.displayMetrics.widthPixels
}
fun isLogin(): Boolean = CommonServiceManager.service<LoginService>()?.isLogin() ?: false
fun goLogin(context: Context) {
    CommonServiceManager.service<LoginService>()?.gotoLoginPage(context)
}
