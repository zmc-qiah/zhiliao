package org.jxxy.debug.theme.http.service

import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.theme.bean.ChatConversation
import org.jxxy.debug.theme.bean.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AiApi {
    @POST("ai/chat")
    suspend fun onceChat(@Header("satoken")value: String, @Query("question")text:String): BaseResp<String>
    @POST("ai/historyChat")
    suspend fun multipleChat(@Header("satoken")value: String, @Body chatConversation: ChatConversation): BaseResp<String>
    @POST("ai/historyChat")
    fun chat(@Header("satoken")value: String, @Body chatConversation: ChatConversation): Call<String>
    @GET("ai/chat/getInfo")
    suspend fun getPicture(@Header("satoken")value: String):BaseResp<List<UserInfo>>
}