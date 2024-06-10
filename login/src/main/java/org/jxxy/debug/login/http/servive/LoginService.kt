package org.jxxy.debug.login.http.servive

import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.login.bean.LoginData
import org.jxxy.debug.login.http.respone.LoginRespone
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {

    @POST("user/login")
    suspend fun login(@Query("userPhone") userPhone: String, @Query("password") password: String): BaseResp<LoginRespone?>
    @POST("user/register")
    suspend fun register(@Body loginData: LoginData): BaseResp<LoginRespone>
    @GET("user/isRegister")
    suspend fun isRegister(@Query("phone") userPhone: String): BaseResp<Any>

    @GET("user/isLogin")
    suspend fun isLogin(): BaseResp<Boolean>

    @GET("user/logout")
    suspend fun logout(): BaseResp<Any>

    @GET("home/init")
    suspend fun init(@Header("satoken") satoken: String)
}
