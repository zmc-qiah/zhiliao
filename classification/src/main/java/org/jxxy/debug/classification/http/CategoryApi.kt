package com.jxxy.debug.category.http.service

import org.jxxy.debug.common.http.Response.CategoryDetailResponse
import org.jxxy.debug.common.http.Response.ResourceTypeList
import org.jxxy.debug.corekit.http.bean.BaseResp
import response.ResouceResponse
import response.ResourceList
import response.ResourceSecond
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryApi {
    @GET("classify/tab/get")
    suspend fun getAll(): BaseResp<CategoryDetailResponse>

    @GET("classify/resource/get")
    suspend fun getResource(@Query("tabId") tabId: Int): BaseResp<ResouceResponse>

//    @GET("home/getSearchBoxDataList")
//    suspend fun getSearchBoxDataList(): BaseResp<Search>
}
