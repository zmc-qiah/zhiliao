package org.jxxy.debug.common.http.service

import org.jxxy.debug.common.bean.AiBookMarkRes
import org.jxxy.debug.common.bean.BookMarkRes
import org.jxxy.debug.corekit.http.bean.BaseResp
import retrofit2.http.GET
import retrofit2.http.Query

interface BookMarkService {
    @GET("resource/practice")
    suspend fun getBookMarkScheme(@Query("resourceId") id: Int): BaseResp<BookMarkRes>
    @GET("resource/review")
    suspend fun getAiBookMarkScheme(@Query("AiId") id : Int) : BaseResp<AiBookMarkRes>
}