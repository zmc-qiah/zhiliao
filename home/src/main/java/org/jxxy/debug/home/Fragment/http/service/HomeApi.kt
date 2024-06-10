package org.jxxy.debug.home.Fragment.http.service

import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.home.Fragment.http.response.*
import org.jxxy.debug.home.Fragment.item.Fall
import org.jxxy.debug.home.Fragment.item.StudyFloor
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {
    @GET("http://47.115.202.222:8899/home/get")
    suspend fun getHomeFloor():BaseResp<HomeFloor>

    @GET("home/fall/get")
    suspend fun getFalls(
        @Query("start") start: Int,
        @Query("pageSize") pageSize: Int
    ): BaseResp<Fall>

    @GET("http://47.115.202.222:8899/home/get")
    suspend fun getStudyFloor(@Query("type") type: Int = 2): BaseResp<StudyFloor>

    @GET("http://47.115.202.222:8899/home/get")
    suspend fun getPracticeFloor(@Query("type") type: Int = 3): BaseResp<PracticeFloor>

    @GET("home/tab/get")
    suspend fun getTab(): BaseResp<TabInfosRespone>

    @GET("home/get")
    suspend fun getHome(@Query("type") type: Int):BaseResp<CommonRespone>

    @GET("identity/getById")
    suspend fun getIdentity(@Query("identityId") identityId: Int):BaseResp<String>

}