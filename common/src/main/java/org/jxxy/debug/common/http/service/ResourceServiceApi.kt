package org.jxxy.debug.common.http.service

import okhttp3.MultipartBody
import org.jxxy.debug.corekit.http.bean.BaseResp
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ResourceServiceApi {

    @POST("resource/like/add")
    suspend fun addLike(@Header("sotoken") value: String, @Query("resourceId") id: Int): BaseResp<Int>

    @POST("resource/like/cancel")
    suspend fun cancelLike(@Header("sotoken") value: String, @Query("resourceId") id: Int): BaseResp<Int>

    @POST("resource/collection/add")
    suspend fun addCollection(@Header("sotoken") value: String, @Query("resourceId") id: Int): BaseResp<Int>

    @POST("resource/collection/cancel")
    suspend fun cancelCollection(@Header("sotoken") value: String, @Query("resourceId") id: Int): BaseResp<Int>

    @Multipart
    @POST("resource/upload")
    suspend fun uploadImage(@Part file: MultipartBody.Part):BaseResp<String>
}
