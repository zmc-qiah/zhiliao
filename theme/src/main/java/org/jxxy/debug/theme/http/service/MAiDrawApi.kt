package org.jxxy.debug.theme.http.service

import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.theme.bean.*
import retrofit2.http.*

interface MAiDrawApi {
    @POST("ai/draw/create")
    suspend fun getPictureId(@Query("enhance") enhance: Int,@Body body: AiDrawBodySimple): BaseResp<String>

    @GET("ai/draw/get")
    suspend fun getPictureByID(
        @Query("id") id: String
    ): BaseResp<String>
}