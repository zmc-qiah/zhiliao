package org.jxxy.debug.login.http.repository

import android.util.Log
import org.jxxy.debug.corekit.http.HttpManager
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.corekit.util.service
import org.jxxy.debug.login.bean.LoginData
import org.jxxy.debug.login.http.respone.LoginRespone
import org.jxxy.debug.login.http.servive.LoginService

class LoginRepository {
    val TAG = "LoginRepository"
    val apiService: LoginService by lazy {
        HttpManager.instance.service()
    }
    suspend fun login(phone: String, password: String): BaseResp<LoginRespone?> = apiService.login(phone, password)
    suspend fun register(phone: String, password: String): BaseResp<LoginRespone> {
        return apiService.register(LoginData(phone, password))
    }
    suspend fun isRegistered(phone: String): BaseResp<Any> {
        return apiService.isRegister(phone)
    }
    suspend fun init(value:String) {
        apiService.init(value)
    }
}
