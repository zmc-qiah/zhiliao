package org.jxxy.debug.theme.http.service

import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.theme.bean.AiDrawBody
import org.jxxy.debug.theme.bean.AiDrawPictureRes
import org.jxxy.debug.theme.bean.AiDrawRes
import org.jxxy.debug.theme.bean.AiDrawResp
import retrofit2.http.*

interface AiDrawApi {
    @POST("open-task")
    suspend fun getPictureId(@Header("ys-api-key") header: String,@Body body : AiDrawBody) : AiDrawResp<AiDrawRes>

    @GET("open-task")
    suspend fun getPictureByID(@Header("ys-api-key") header: String,@Query("id") id : String) : AiDrawResp<AiDrawPictureRes>
}