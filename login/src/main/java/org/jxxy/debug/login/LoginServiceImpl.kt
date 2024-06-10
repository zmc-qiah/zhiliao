package org.jxxy.debug.login

import android.content.Context
import com.google.auto.service.AutoService
import org.jxxy.debug.common.service.LoginService
import org.jxxy.debug.corekit.http.TokenManager
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.login.activity.LoginActivity
import kotlin.math.log

@AutoService(LoginService::class)
class LoginServiceImpl : LoginService {
    override fun gotoLoginPage(context: Context) {
        context.startActivity(LoginActivity::class.java)
    }
    override fun gotoRegister(context: Context) {
        context.startActivity(LoginActivity::class.java)
    }
    override fun isLogin(): Boolean = TokenManager.getToken() != null
}
