package org.jxxy.debug.resources.http.service

import org.jxxy.debug.corekit.http.bean.BaseResp
import retrofit2.http.Header
import retrofit2.http.POST

interface PointAPI {
    @POST("score/addArticle")
   suspend fun addArticle(@Header("satoken") value: String):BaseResp<String?>
    @POST("score/addView")
    suspend fun addView(@Header("satoken") value: String):BaseResp<String?>
}