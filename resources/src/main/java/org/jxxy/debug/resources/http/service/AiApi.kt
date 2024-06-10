package org.jxxy.debug.resources.http.service

import okhttp3.ResponseBody
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.resources.bean.FastGPTChatBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Streaming

interface AiApi {
    @POST("ai/chat")
    suspend fun onceChat(@Header ("satoken")value: String,@Body text:String):BaseResp<String>
//    @Headers("Content-Type: application/json")
//    @POST("chat/completions")
//    @Streaming
//    fun streamChat(@Body data: FastGPTChatBody, @Header("Authorization") value: String): Call<ResponseBody>
}