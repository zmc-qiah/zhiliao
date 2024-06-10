package org.jxxy.debug.society.http.viewModel

import android.app.Application
import org.jxxy.debug.common.http.BaseLiveDataCallback2
import org.jxxy.debug.corekit.http.BaseViewModel
import org.jxxy.debug.corekit.http.request
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.society.http.reponsitory.SocialRepository
import org.jxxy.debug.society.http.response.AiImgResponse
import org.jxxy.debug.society.http.response.DiscussDetailResponse
import org.jxxy.debug.society.http.response.DiscussResponse
import org.jxxy.debug.society.http.response.ExcellentClassResponse
import org.jxxy.debug.society.http.response.ExpShareResponse
import org.jxxy.debug.society.http.response.HistoryResponse
import org.jxxy.debug.society.http.response.LectureResponse
import org.jxxy.debug.society.http.response.MovableResponse
import org.jxxy.debug.society.http.response.Playresponse
import org.jxxy.debug.society.http.response.RankQuestionResponse


class SocialViewModel(app: Application) : BaseViewModel(app) {
    companion object {

    }
    val repository: SocialRepository by lazy { SocialRepository() }
    val AiImgData : ResLiveData<AiImgResponse?> by lazy { ResLiveData() }
    val DiscussData : ResLiveData<DiscussResponse?> by lazy { ResLiveData() }
    val HistoryData : ResLiveData<HistoryResponse?> by lazy { ResLiveData() }
    val rankQuestionData : ResLiveData<RankQuestionResponse?> by lazy { ResLiveData() }
    val lecturegetData : ResLiveData<LectureResponse?> by lazy { ResLiveData() }
    val activitygetData : ResLiveData<MovableResponse?> by lazy { ResLiveData() }
    val discussiongetData : ResLiveData<Playresponse?> by lazy { ResLiveData() }
    val classgetData : ResLiveData<ExcellentClassResponse?> by lazy { ResLiveData() }
    val sharegetData : ResLiveData<ExpShareResponse?> by lazy { ResLiveData() }
    val DiscussDetailData : ResLiveData<DiscussDetailResponse?> by lazy { ResLiveData() }

    //获取搜索提示词
    fun AiImg() {
        request(
            AiImgData,
            object : BaseLiveDataCallback2<AiImgResponse?> {}
        ) {
            repository.AiImg()
        }
    }
    fun DiscussgetAll() {
        request(
            DiscussData,
            object : BaseLiveDataCallback2<DiscussResponse?> {}
        ) {
            repository.DiscussgetAll()
        }
    }
    fun Historyget(){
        request(HistoryData,
        object :BaseLiveDataCallback2<HistoryResponse?> {})
        {
            repository.Historyget()
        }
    }
    fun rankQuestion(){
        request(rankQuestionData,
            object :BaseLiveDataCallback2<RankQuestionResponse?> {})
        {
            repository.rankQuestion()
        }
    }
    fun lectureget(){
        request(lecturegetData,
            object :BaseLiveDataCallback2<LectureResponse?> {})
        {
            repository.lectureget()
        }
    }
    fun activityget(){
        request(activitygetData,
            object :BaseLiveDataCallback2<MovableResponse?> {})
        {
            repository.activityget()
        }
    }
    fun discussionget(){
        request(discussiongetData,
            object :BaseLiveDataCallback2<Playresponse?> {})
        {
            repository.discussionget()
        }
    }
    fun classget(classchoice: Int){
        request(classgetData,
            object :BaseLiveDataCallback2<ExcellentClassResponse?> {})
        {
            repository.classget(classchoice)
        }
    }
    fun shareget(){
        request(sharegetData,
            object :BaseLiveDataCallback2<ExpShareResponse?> {})
        {
            repository.shareget ()
        }
    }

    fun getDetail(AnswerId:Int){
        request(DiscussDetailData,
            object :BaseLiveDataCallback2<DiscussDetailResponse?> {})
        {
            repository.getDetail(AnswerId)
        }
    }

}
