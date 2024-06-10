package org.jxxy.debug.member.http.service

import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.member.bean.LoginData
import org.jxxy.debug.member.bean.Plan
import org.jxxy.debug.member.bean.PlanEvent
import org.jxxy.debug.member.bean.PostPlan
import org.jxxy.debug.member.http.respone.FollowRespone
import org.jxxy.debug.member.http.respone.GroupForumResponse
import org.jxxy.debug.member.http.respone.HistoryRespone
import org.jxxy.debug.member.http.respone.InfoRespone
import org.jxxy.debug.member.http.respone.MarkRespone
import org.jxxy.debug.member.http.respone.MemberRespone
import org.jxxy.debug.member.http.respone.NoteRespone
import org.jxxy.debug.member.http.respone.WeekReportResponce
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface MemberApi {
    @GET("member")
    suspend fun getMember(@Header("satoken") value: String): BaseResp<MemberRespone>

    @GET("member/follower/get")
    suspend fun getMemberFellow(@Header("satoken") value: String): BaseResp<FollowRespone>

    @GET("member/note/get")
    suspend fun getMemberNote(@Header("satoken") value: String): BaseResp<NoteRespone>

    @GET("member/history/get")
    suspend fun getMemberHistory(@Header("satoken") value: String): BaseResp<HistoryRespone>

    @GET("member/collection/get")
    suspend fun getMemberMark(@Header("satoken") value: String): BaseResp<MarkRespone>

    @GET("member/plan/get")
    suspend fun getMemberPlan(@Header("satoken") value: String): BaseResp<ArrayList<Plan>>

    @GET("member/plan/event/get")
    suspend fun getMemberEvent(@Header("satoken") value: String, @Query("planId") id: Int): BaseResp<ArrayList<PlanEvent>>

    @POST("member/plan/post")
    suspend fun postPlan(
        @Header("satoken") value: String,
        @Query("planName") name: String,
        @Query("startTime") startTime: String,
        @Query("endTime") endTime: String,
        @Body plan: PostPlan
    ): BaseResp<Int?>

    @GET("user/logout")
    suspend fun logout(@Header("satoken") value: String): BaseResp<Any>

    @POST("user/login")
    suspend fun login(@Query("userPhone") userPhone: String, @Query("password") password: String): BaseResp<LoginData>

    @GET("member/info/get")
    suspend fun getInfo(@Header("satoken") value: String): BaseResp<InfoRespone>

    @GET("member/forum/get")
    suspend fun getForum(@Header("satoken") value: String, @Query("start") start: Int, @Query("pageSize") pageSize: Int): BaseResp<GroupForumResponse>

    @GET("member/report/get")
    suspend fun getReport(@Header("satoken") value: String): BaseResp<WeekReportResponce>
}
