package org.jxxy.debug.member.http.service

import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.member.bean.MemberGroup
import org.jxxy.debug.member.bean.TaskRespone
import org.jxxy.debug.member.http.respone.PointDetailRespone
import retrofit2.http.GET
import retrofit2.http.Header

interface PointAPI {
    @GET("score/getTask")
    suspend fun getTask(@Header("satoken") value: String): BaseResp<TaskRespone>

    @GET("score/getRank")
    suspend fun getRank(@Header("satoken") value: String): BaseResp<MemberGroup>

    @GET("score/getDetail")
    suspend fun getDetail(@Header("satoken") value: String): BaseResp<PointDetailRespone>

}
