package org.jxxy.debug.theme.http.service

import org.jxxy.debug.theme.bean.RespondBean
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BaiduApi {
    @FormUrlEncoded
    @POST("translate")
    fun translate(
        @Field("q") q: String,
        @Field("from") from: String,
        @Field("to") to: String,
        @Field("appid") appid: String,
        @Field("salt") salt: String,
        @Field("sign") sign: String
    ): Call<RespondBean>
}