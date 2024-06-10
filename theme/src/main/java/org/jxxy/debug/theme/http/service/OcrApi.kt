package org.jxxy.debug.theme.http.service

import okhttp3.FormBody
import org.jxxy.debug.theme.bean.OcrRes
import org.jxxy.debug.theme.bean.ReadSomethingResult
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface OcrApi {
    @POST("rest/2.0/image-classify/v2/advanced_general")
    suspend fun readSomething(
        @Query("access_token") token: String,
        @Header("Content-Type") type: String,
        @Body body: FormBody
    ): ReadSomethingResult

    @POST("rest/2.0/ocr/v1/general_basic")
    suspend fun ocrUse(
        @Query("access_token") token: String,
        @Header("Content-Type") type: String,
        @Body body : FormBody
    ) : OcrRes
}