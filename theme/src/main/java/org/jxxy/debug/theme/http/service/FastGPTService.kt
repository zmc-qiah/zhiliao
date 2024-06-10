package org.jxxy.debug.theme.http.service
import okhttp3.ResponseBody
import org.jxxy.debug.theme.bean.FastGPTChatBody
import org.jxxy.debug.theme.bean.FastResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Streaming

interface FastGPTService {
    @Headers("Content-Type: application/json")
    @POST("chat/completions")
    suspend fun chat(@Body chatMessage: FastGPTChatBody, @Header("Authorization") value: String): FastResponse
    @Headers("Content-Type: application/json")
    @POST("chat/completions")
    @Streaming
    fun streamChat(@Body data: FastGPTChatBody, @Header("Authorization") value: String): Call<ResponseBody> // 注意：这里的返回类型使用 Call<ResponseBody>
}
