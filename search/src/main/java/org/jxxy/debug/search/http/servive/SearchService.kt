package org.jxxy.debug.search.http.servive
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.search.http.respone.SearchContentRespone
import org.jxxy.debug.search.http.respone.SearchGetResponse
import org.jxxy.debug.search.http.respone.SearchRespone
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    //搜索初始页面
    @GET("search")
    suspend fun search(): BaseResp<SearchRespone?>
    //搜索关联词,参数是搜索内容
    @GET("search/content")
    suspend fun searchcontent(@Query("keyword") searchcontent: String): BaseResp<SearchContentRespone?>
    //搜索结果预览页，参数是搜索内容
    @GET("search/content/get")
    suspend fun searchget(@Query("keyword") searchcontent: String): BaseResp<SearchGetResponse?>
    @GET("search/content/full/get")
    suspend fun fall(@Query("keyword") searchcontent: String,@Query("start") a: Int,@Query("pageSize") pageSize: Int,): BaseResp<SearchGetResponse>
}
