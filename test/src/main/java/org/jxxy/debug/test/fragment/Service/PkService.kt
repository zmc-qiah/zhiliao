package org.jxxy.debug.test.fragment.Service

import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.test.fragment.bean.PkMessage
import org.jxxy.debug.test.fragment.bean.PkQuestion
import org.jxxy.debug.test.fragment.bean.UserInfo
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface PkService {

    @POST("question/pk/connect")
    suspend fun joinPk(@Header("satoken") value: String): BaseResp<UserInfo>
    @POST("question/pk/connect")
    suspend fun joinPk1(@Header("satoken") value: String): BaseResp<Int>

    @GET("question/pk/getJoin")
    suspend fun getJoin(@Header("satoken") value: String): BaseResp<Int>

    @GET("question/pk/getQuestion")
    suspend fun getPkQuestion(@Header("satoken") value: String): BaseResp<PkQuestion>

    @POST("question/pk/post")
    suspend fun postAnswerMessage(
        @Header("satoken") value: String,
        @Query("score") score: Int,
        @Query("time") time : Int,
    ): BaseResp<PkMessage>

    @GET("question/pk/getInfo")
    suspend fun getAnswerMessage(@Header("satoken") value: String) : BaseResp<PkMessage>

    @GET("question/pk/finish")
    suspend fun finishPk(@Header("satoken") value: String) : BaseResp<Int>
}