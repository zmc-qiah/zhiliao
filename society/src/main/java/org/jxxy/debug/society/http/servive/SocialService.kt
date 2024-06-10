package org.jxxy.debug.society.http.servive
import org.jxxy.debug.corekit.http.bean.BaseResp
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
import retrofit2.http.GET
import retrofit2.http.Query

interface SocialService {
    //查询ai绘画
    @GET("social/AiImg/get")
    suspend fun AiImg(): BaseResp<AiImgResponse?>
    //查询所有讨论

    @GET("social/discuss/answer/getAll")
    suspend fun disgussgetAll(): BaseResp<DiscussResponse?>

    @GET("home/history/get")
    suspend fun historyget(): BaseResp<HistoryResponse?>

    @GET("social/discuss/rankQuestion")
    suspend fun rankQuestion(): BaseResp<RankQuestionResponse?>

    @GET("home/lecture/get")
    suspend fun lectureget(): BaseResp<LectureResponse?>
    @GET("home/activity/get")
    suspend fun activityget(): BaseResp<MovableResponse?>

    @GET("home/discussion/get")
    suspend fun discussionget(): BaseResp<Playresponse?>

    @GET("home/class/get")
    suspend fun classget(@Query("id") classchoice: Int): BaseResp<ExcellentClassResponse?>

    @GET("home/activity/share/get")
    suspend fun shareget(): BaseResp<ExpShareResponse?>

    @GET("social/discuss/answer/getDetail")
    suspend fun getDetail(@Query("AnswerId") AnswerId: Int): BaseResp<DiscussDetailResponse?>


}
