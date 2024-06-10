package org.jxxy.debug.thinkMap.http

import org.jxxy.debug.common.bean.Resource
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.thinkMap.bean.ThinkMapRespone
import org.jxxy.debug.thinkMap.bean.User
import org.jxxy.debug.thinkMap.bean.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Service {
    @GET("member/info/get")
    suspend fun getInfo():BaseResp<User>
    @POST("thinkMap/together")
    suspend fun postTogether(@Body userInfo: UserInfo):BaseResp<List<UserInfo>>

    @POST("thinkMap/together")
     fun postTogether2(@Body userInfo: UserInfo): Call<BaseResp<List<UserInfo>>>

     @GET("thinkMap/aa")
     fun aa():Call<String>
    @GET("resource/mindMap/getById")
    suspend fun getThinkMapById(@Query("id") id:Int):BaseResp<ThinkMapRespone>
    @GET("resource/getInfo")
    suspend fun getInfo(@Query("resourceId") id:Int):BaseResp<Resource>
    @POST("resource/mindMap/update")
    suspend fun updateMap(@Body body:ThinkMapRespone):BaseResp<ThinkMapRespone>
    @POST("resource/mindMap/insert")
    suspend fun save(@Body body:ThinkMapRespone):BaseResp<ThinkMapRespone>
}