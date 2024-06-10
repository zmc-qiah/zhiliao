package org.jxxy.debug.search.http.viewModel

import android.app.Application
import org.jxxy.debug.common.http.BaseLiveDataCallback2
import org.jxxy.debug.corekit.http.BaseViewModel
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.request
import org.jxxy.debug.search.http.repository.SearchRepository
import org.jxxy.debug.search.http.respone.SearchContentRespone
import org.jxxy.debug.search.http.respone.SearchGetResponse
import org.jxxy.debug.search.http.respone.SearchRespone

class SearchViewModel(app: Application) : BaseViewModel(app) {
    companion object {

    }
    val repository: SearchRepository by lazy { SearchRepository() }
    val SearchcontentData: ResLiveData<SearchContentRespone?> by lazy { ResLiveData() }
    val SearchData : ResLiveData<SearchRespone?> by lazy { ResLiveData() }
    val SearchgetData : ResLiveData<SearchGetResponse?> by lazy { ResLiveData() }
    //获取搜索提示词
    fun getSearch(searchcontent:String) {
        request(
            SearchcontentData,
            object : BaseLiveDataCallback2<SearchContentRespone?> {}
        ) {
            repository.getSeacrh(searchcontent)
        }
    }
    //搜索初始页内容
    fun Search() {
        request(
            SearchData,
            object : BaseLiveDataCallback2<SearchRespone?> {}
        ) {
            repository.search()
        }
    }
    //搜索结果页预览内容
    fun Searchget(searchcontent:String){
        request(
            SearchgetData,
            object : BaseLiveDataCallback2<SearchGetResponse?> {}
        ) {
            repository.Seacrhget(searchcontent)
        }
    }
    val f :ResLiveData<SearchGetResponse> by lazy { ResLiveData() }
    fun f(searchcontent:String,s:Int){
        request(
            f,
            object : BaseLiveDataCallback2<SearchGetResponse> {}
        ) {
            repository.fall(searchcontent,s,6)
        }
    }


}
