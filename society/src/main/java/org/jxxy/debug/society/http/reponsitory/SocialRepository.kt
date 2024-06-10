package org.jxxy.debug.society.http.reponsitory

import org.jxxy.debug.corekit.http.HttpManager
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.corekit.util.service
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
import org.jxxy.debug.society.http.servive.SocialService

class SocialRepository {
    val TAG = "SocialRepository"
    val apiService: SocialService by lazy {
        HttpManager.instance.service()
    }


    suspend fun AiImg(): BaseResp<AiImgResponse?> {
        return apiService.AiImg()
    }
    suspend fun DiscussgetAll(): BaseResp<DiscussResponse?> {
        return apiService.disgussgetAll()
    }
    suspend fun Historyget(): BaseResp<HistoryResponse?> {
        return apiService.historyget()
    }
    suspend fun rankQuestion(): BaseResp<RankQuestionResponse?> {
        return apiService.rankQuestion ()
    }
    suspend fun lectureget(): BaseResp<LectureResponse?> {
        return apiService.lectureget ()
    }
    suspend fun activityget(): BaseResp<MovableResponse?> {
        return apiService.activityget ()
    }
    suspend fun discussionget(): BaseResp<Playresponse?> {
        return apiService.discussionget ()
    }
    suspend fun classget(classchoice: Int): BaseResp<ExcellentClassResponse?> {
        return apiService.classget (classchoice)
    }
    suspend fun shareget(): BaseResp<ExpShareResponse?> {
        return apiService.shareget ()
    }
    suspend fun getDetail(AnswerId:Int): BaseResp<DiscussDetailResponse?> {
        return apiService.getDetail (AnswerId)
    }

}
