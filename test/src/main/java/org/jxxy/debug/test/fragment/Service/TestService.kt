package org.jxxy.debug.test.fragment.Service

import com.google.android.exoplayer2.text.span.TextAnnotation
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.test.fragment.bean.*
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface TestService {

    @GET("question/get")
    suspend fun getQuestion(
        @Header("sotoken") value: String,
        @Query("count") count: Int
    ): BaseResp<Questions>

    @GET("question/mistake/get")
    suspend fun getMistake(@Header("sotoken") value: String): BaseResp<CollectionOrMistakeQuestions>

    @GET("question/collection/get")
    suspend fun getCollection(@Header("sotoken") value: String): BaseResp<CollectionOrMistakeQuestions>

    @GET("question/category/get")
    suspend fun getSpecialQuestion(
        @Header("sotoken") value: String,
        @Query("category") category: String,
        @Query("start") start: Int,
        @Query("pageSize") pageSize: Int
    ): BaseResp<SpecialQuestions>

    @GET("question/getById")
    suspend fun getQuestionById(
        @Header("sotoken") value: String,
        @Query("questionId") questionId: Int
    ): BaseResp<Question>

    @GET("question/statistics")
    suspend fun getStatistics(@Header("sotoken") value: String): BaseResp<StatisticsContent>

    @POST("question/collection/post")
    suspend fun addCollection(
        @Header("sotoken") value: String,
        @Query("questionId") questionId: Int
    ): BaseResp<Int>

    @POST("question/collection/delete")
    suspend fun deleteCollection(
        @Header("sotoken") value: String,
        @Query("questionId") questionId: Int
    ): BaseResp<Int>

    @POST("question/mistake/post")
    suspend fun addMistake(
        @Header("sotoken") value: String,
        @Query("questionId") questionId: Int
    ): BaseResp<Int>

    @POST("question/mistake/delete")
    suspend fun deleteMistake(
        @Header("sotoken") value: String,
        @Query("questionId") questionId: Int
    ): BaseResp<Int>

    @POST("score/addAnswer")
    suspend fun addAnswer(@Header("sotoken") value: String): BaseResp<Int>

    @GET("question/special")
    suspend fun getSpecialOut(@Header("sotoken") value: String): BaseResp<SpecialInfo>

    @GET("question/special/get")
    suspend fun getSpecialInner(
        @Header("sotoken") value: String,
        @Query("category") category: String,
        @Query("start") start: Int,
        @Query("pageSize") pageSize: Int
    ): BaseResp<SpecialInner>

    @POST("question/info/post")
    suspend fun postAnswer(
        @Header("sotoken") value: String,
        @Query("type") type: Int,
        @Query("questionId") questionId: Int
    ): BaseResp<Int>

    @GET("question/special/rank")
    suspend fun getSpecialRank(@Header("sotoken") value: String,@Query("specialId") specialId:Int): BaseResp<SpecialRanks>

    @POST("question/special/finish")
    suspend fun postSpecialScore(
        @Header("sotoken") value: String,
        @Query("specialId") specialId: Int,
        @Query("time") time: Int,
        @Query("score") score: Int
    ) : BaseResp<Int>

}