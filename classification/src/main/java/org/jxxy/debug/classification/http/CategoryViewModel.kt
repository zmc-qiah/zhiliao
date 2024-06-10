package com.jxxy.debug.category.http.viewmodel

import android.app.Application
import com.jxxy.debug.category.http.Repository.CategoryRepository
import org.jxxy.debug.common.http.BaseLiveDataCallback2
import org.jxxy.debug.common.http.Response.CategoryDetailResponse
import org.jxxy.debug.common.http.Response.ResourceTypeList
import org.jxxy.debug.corekit.http.BaseViewModel
import org.jxxy.debug.corekit.http.bean.ErrorResponse
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.listener.LiveDataCallback
import org.jxxy.debug.corekit.http.request
import response.ResouceResponse
import response.ResourceList
import response.ResourceSecond

class CategoryViewModel(application: Application) : BaseViewModel(application) {
    private val repository: CategoryRepository by lazy {
        CategoryRepository()
    }

    val liveData: ResLiveData<CategoryDetailResponse> by lazy { ResLiveData() }
    val resourceListLiveData: ResLiveData<ResouceResponse> by lazy { ResLiveData() }
//    val searchLiveData: ResLiveData<Search> by lazy { ResLiveData() }

    fun getAll() {
        request(
            liveData,
            object : BaseLiveDataCallback2<CategoryDetailResponse>{}
        ) {
            repository.getAll()
        }
    }


    fun getResourceList(tabId: Int){
        request(
            resourceListLiveData,
            object :BaseLiveDataCallback2<ResouceResponse>{}
        ){
            repository.getResource(tabId)
        }
    }

//    fun getSearchBoxDataList() {
//        request(
//            searchLiveData,
//            object : LiveDataCallback<Search, Search> {
//                override fun error(emit: ResLiveData<Search>, e: ErrorResponse) {
//                }
//
//                override fun otherCode(
//                    emit: ResLiveData<Search>,
//                    code: Int?,
//                    msg: String?,
//                    data: Search?
//                ) {
//                }
//
//                override fun success(emit: ResLiveData<Search>, msg: String?, data: Search?) {
//                    data?.let { emit.success(it) }
//                }
//            }
//        ) {
//            repository.getSearchBoxDataList()
//        }
//    }
}
