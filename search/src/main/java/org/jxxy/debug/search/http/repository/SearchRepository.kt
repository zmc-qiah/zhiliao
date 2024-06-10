package org.jxxy.debug.search.http.repository

import android.util.Log
import org.jxxy.debug.corekit.http.HttpManager
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.corekit.util.service
import org.jxxy.debug.search.http.respone.SearchContentRespone
import org.jxxy.debug.search.http.respone.SearchGetResponse
import org.jxxy.debug.search.http.respone.SearchRespone
import org.jxxy.debug.search.http.servive.SearchService

class SearchRepository {
    val TAG = "SearchRepository"
    val apiService: SearchService by lazy {
        HttpManager.instance.service()
    }

    suspend fun getSeacrh(searchcontent: String): BaseResp<SearchContentRespone?> {
        var searchmessage = apiService.searchcontent(searchcontent)
      /*  Log.d(TAG, "login:1  " + searchmessage.message)
        Log.d(TAG, "login: 2 " + searchmessage.data)
        Log.d(TAG, "login: 3 " + searchmessage.data)*/
        return searchmessage
    }

    suspend fun search(): BaseResp<SearchRespone?> {
        return apiService.search()
    }
    suspend fun Seacrhget(searchcontent: String): BaseResp<SearchGetResponse?> {
        var searchmessage = apiService.searchget(searchcontent)
        Log.d(TAG, "login:1  " + searchmessage.message)
        Log.d(TAG, "login: 2 " + searchmessage.data)
        Log.d(TAG, "login: 3 " + searchmessage.data)
        return searchmessage
    }
    suspend fun fall(key: String,s:Int,p:Int): BaseResp<SearchGetResponse> = apiService.fall(key,s,p)
}
