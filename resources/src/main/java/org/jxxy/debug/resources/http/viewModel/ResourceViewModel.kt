package org.jxxy.debug.resources.http.viewModel

import android.app.Application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jxxy.debug.common.bean.Node
import org.jxxy.debug.common.http.BaseLiveDataCallback2
import org.jxxy.debug.corekit.http.BaseViewModel
import org.jxxy.debug.corekit.http.TokenManager
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.request
import org.jxxy.debug.resources.bean.ChapterCommentBody
import org.jxxy.debug.resources.bean.ChapterCommentResponse
import org.jxxy.debug.resources.bean.TagInfo
import org.jxxy.debug.resources.http.repository.AiRepository
import org.jxxy.debug.resources.http.repository.PointRepository
import org.jxxy.debug.resources.http.repository.ResourceRepository
import org.jxxy.debug.resources.http.response.CommentResponse
import org.jxxy.debug.resources.http.response.RecommendInfoResponse
import org.jxxy.debug.resources.http.response.ResourceInfoResponse

class ResourceViewModel(app: Application) : BaseViewModel(app) {
    val repository: ResourceRepository by lazy { ResourceRepository() }
    val resourceLiveData: ResLiveData<ResourceInfoResponse> by lazy { ResLiveData() }
    fun getResourceById(id: Int) {
        request(
            resourceLiveData,
            object : BaseLiveDataCallback2<ResourceInfoResponse> {}
        ) {
            repository.getResourceById(id)
        }
    }
    val recommendLiveData: ResLiveData<RecommendInfoResponse> by lazy { ResLiveData() }
    fun getRecommend() {
        request(
            recommendLiveData,
            object : BaseLiveDataCallback2<RecommendInfoResponse> {}
        ) {
            repository.getRecommend()
        }
    }
    val likeLiveData: ResLiveData<Int> by lazy { ResLiveData() }
    fun addLike(id: Int) {
        request(
            likeLiveData,
            object : BaseLiveDataCallback2<Int> {}
        ) {
            repository.addLike(id)
        }
    }
    fun cancelLike(id: Int) {
        request(
            likeLiveData,
            object : BaseLiveDataCallback2<Int> {
                override fun success(emit: ResLiveData<Int>, msg: String?, data: Int?) {
                    var like = emit.data ?: 1
                    like -= 1
                    emit.success(like)
                }
            }
        ) {
            repository.cancelLike(id)
        }
    }
    val addMarkLiveData: ResLiveData<Int> by lazy { ResLiveData() }
    var a = 0
    fun addMark(id: Int) {
        request(
            addMarkLiveData,
            object : BaseLiveDataCallback2<Int> {
                override fun success(emit: ResLiveData<Int>, msg: String?, data: Int?) {
                    emit.success(a++)
                }
            }
        ) {
            repository.addCollection(id)
        }
    }
    val cancelMarkLiveData: ResLiveData<Int> by lazy { ResLiveData() }
    fun cancelCollection(id: Int) {
        request(
            cancelMarkLiveData,
            object : BaseLiveDataCallback2<Int> {
                override fun success(emit: ResLiveData<Int>, msg: String?, data: Int?) {
                    emit.success(a++)
                }
            }
        ) {
            repository.cancelCollection(id)
        }
    }
    val commentLiveData: ResLiveData<CommentResponse> by lazy { ResLiveData() }
    fun getComment(id: Int, start: Int, page: Int) {
        request(
            commentLiveData,
            object : BaseLiveDataCallback2<CommentResponse> {}
        ) {
            repository.getComment(id, start, page)
        }
    }
    val addCommentLiveData: ResLiveData<CommentResponse> by lazy { ResLiveData() }
    fun addComment(id:Int,comment: String, commentPhoto: String = "") {
        request(
            addCommentLiveData,
            object : BaseLiveDataCallback2<CommentResponse> {}
        ) {
            repository.addComment(id,comment, commentPhoto)
        }
    }
    fun addNote(id:Int,content:String){
        viewModelScope.launch(Dispatchers.IO) {
            TokenManager.getToken()?.let { repository.APiService.addNote(it,id,content) }
        }
    }
    val pointLiveData: ResLiveData<String?> by lazy { ResLiveData()}
    val pointRepository:PointRepository by lazy { PointRepository()}
    fun addText(){
        request(
            pointLiveData,
            object :BaseLiveDataCallback2<String?>{}
        ){
            pointRepository.addArticle()
        }
    }
    fun addVideo(){
        request(
            pointLiveData,
            object :BaseLiveDataCallback2<String?>{}
        ){
            pointRepository.addView()
        }
    }
    val nodeLiveData by lazy { ResLiveData<Node>() }
    val aiRepositoy = AiRepository()
    fun aiAnalysisTabulation(text:String){
        request(nodeLiveData, object :BaseLiveDataCallback2<Node>{}){aiRepositoy.AIAnalysisTabulation(text)}
    }
    val chapterLiveData by lazy { ResLiveData<ChapterCommentResponse>() }
    fun getChapterComment(id: Int){
        request(chapterLiveData, object :BaseLiveDataCallback2<ChapterCommentResponse>{}){repository.getChapterComment(id)}
    }
    fun addChapterComment(body: ChapterCommentBody){
        request(chapterLiveData, object :BaseLiveDataCallback2<ChapterCommentResponse>{}){repository.addChapterComment(body)}
    }
    val addChapterLiveData by lazy { ResLiveData<List<TagInfo>>() }

    fun addChapter(tag: TagInfo){
        request(addChapterLiveData, object :BaseLiveDataCallback2<List<TagInfo>>{}){repository.addChapter(tag)}
    }

}
