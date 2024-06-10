package org.jxxy.debug.test.fragment.Service

import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.test.fragment.bean.QuestionVideoInner
import org.jxxy.debug.test.fragment.bean.QuestionVideoList
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface QuestionVideoService {
    @GET("question/video/get")
    suspend fun getQuestionVideoList(@Header("sotoken") value: String) : BaseResp<QuestionVideoList>

    @GET("question/video/getById")
    suspend fun getQuestionVideoById(@Header("sotoken") value: String,@Query("id")id:Int) : BaseResp<QuestionVideoInner>

}