package com.jxxy.debug.category.http.Repository

import com.jxxy.debug.category.http.service.CategoryApi
import org.jxxy.debug.common.http.Response.CategoryDetailResponse
import org.jxxy.debug.common.http.Response.ResourceTypeList
import org.jxxy.debug.corekit.http.HttpManager
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.corekit.util.service
import response.ResouceResponse
import response.ResourceList
import response.ResourceSecond

class CategoryRepository {
    private val service: CategoryApi by lazy {
        HttpManager.instance.service(CategoryApi::class.java)
    }

    suspend fun getAll(): BaseResp<CategoryDetailResponse> {
        return service.getAll()
    }

    suspend fun getResource(tabId:Int):BaseResp<ResouceResponse>{
        return service.getResource(tabId)
    }

//    suspend fun getSearchBoxDataList(): BaseResp<Search> {
//        return service.getSearchBoxDataList()
//    }
}
