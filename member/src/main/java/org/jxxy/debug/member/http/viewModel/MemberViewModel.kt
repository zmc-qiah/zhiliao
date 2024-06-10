package org.jxxy.debug.member.http.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.luck.picture.lib.entity.LocalMedia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.jxxy.debug.common.http.BaseLiveDataCallback2
import org.jxxy.debug.common.http.repository.ResourceRepository
import org.jxxy.debug.corekit.http.BaseViewModel
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.request
import org.jxxy.debug.member.bean.LoginData
import org.jxxy.debug.member.bean.Plan
import org.jxxy.debug.member.bean.PostComment
import org.jxxy.debug.member.bean.Preview
import org.jxxy.debug.member.http.BaseLiveDataCallback
import org.jxxy.debug.member.http.repository.MemberRepository
import org.jxxy.debug.member.http.repository.ResourceRepositoy
import org.jxxy.debug.member.http.respone.FollowRespone
import org.jxxy.debug.member.http.respone.ForumCommentRespone
import org.jxxy.debug.member.http.respone.GroupForumResponse
import org.jxxy.debug.member.http.respone.HistoryRespone
import org.jxxy.debug.member.http.respone.InfoRespone
import org.jxxy.debug.member.http.respone.MarkRespone
import org.jxxy.debug.member.http.respone.MemberRespone
import org.jxxy.debug.member.http.respone.NoteRespone
import org.jxxy.debug.member.http.respone.WeekReportResponce

class MemberViewModel(application: Application) : BaseViewModel(application) {
    companion object {
        var FLAG = 0
    }
    val repository: MemberRepository by lazy { MemberRepository() }
    val memberDataLiveRespone: ResLiveData<MemberRespone> by lazy { ResLiveData() }
    val fellowDataLiveData: ResLiveData<FollowRespone> by lazy { ResLiveData() }
    val noteDataLiveData: ResLiveData<NoteRespone> by lazy { ResLiveData() }
    val markDataLiveData: ResLiveData<MarkRespone> by lazy { ResLiveData() }
    val historyDataLiveData: ResLiveData<HistoryRespone> by lazy { ResLiveData() }
    val loginDataLiveData: ResLiveData<LoginData> by lazy { ResLiveData() }
    val planDataLiveData: ResLiveData<ArrayList<Plan>> by lazy { ResLiveData() }
    val addPlanDataLiveData: ResLiveData<Int?> by lazy { ResLiveData() }
    val userInfoDataLiveData: ResLiveData<InfoRespone> by lazy { ResLiveData() }
    val forumDataLiveData: ResLiveData<GroupForumResponse> by lazy { ResLiveData() }
    fun getMemberNote() {
        request(
            noteDataLiveData,
            object : BaseLiveDataCallback2<NoteRespone> {}
        ) {
            repository.getMemberNote()
        }
    }
    fun getMemberHistory() {
        request(
            historyDataLiveData,
            object : BaseLiveDataCallback2<HistoryRespone> {}
        ) {
            repository.getMemberHistory()
        }
    }
    fun getMemberMark() {
        request(
            markDataLiveData,
            object : BaseLiveDataCallback2<MarkRespone> {}
        ) {
            repository.getMemberMark()
        }
    }

    fun getMemberFellow() {
        request(
            fellowDataLiveData,
            object : BaseLiveDataCallback2<FollowRespone> {}
        ) {
            repository.getMemberFellow()
        }
    }
    fun getPlan() {
        request(
            planDataLiveData,
            object : BaseLiveDataCallback2<ArrayList<Plan>> {}
        ) {
            repository.getMemberFlan()
        }
    }
    fun getMember() {
        request(
            memberDataLiveRespone,
            object : BaseLiveDataCallback2<MemberRespone> {}
        ) {
            repository.getMember()
        }
    }
    fun login() {
        request(
            loginDataLiveData,
            object : BaseLiveDataCallback2<LoginData> {}
        ) {
            repository.login()
        }
    }
    fun addPlan(plan: Plan) {
        request(
            addPlanDataLiveData,
            object : BaseLiveDataCallback<Int?, Int?> {
                override fun success(emit: ResLiveData<Int?>, msg: String?, data: Int?) {
                    emit.success(FLAG++)
                }
            }
        ) {
            repository.addPlan(plan)
        }
    }
    fun getInfo() {
        request(
            userInfoDataLiveData,
            object : BaseLiveDataCallback2<InfoRespone> {}
        ) {
            repository.getInfo()
        }
    }
    fun updateInfo(info: InfoRespone) {
        request(
            userInfoDataLiveData,
            object : BaseLiveDataCallback2<InfoRespone> {}
        ) {
            repository.getInfo()
        }
    }
    fun getForum(start: Int, pageSize: Int) {
        request(
            forumDataLiveData,
            object : BaseLiveDataCallback2<GroupForumResponse> {}
        ) {
            repository.getForum(start, pageSize)
        }
    }
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
    val reportLiveData: ResLiveData<WeekReportResponce> by lazy { ResLiveData() }
    fun getReport() {
        request(
            reportLiveData,
            object : BaseLiveDataCallback2<WeekReportResponce> {}
        ) {
            repository.getReport()
        }
    }
    val forumRepository = ResourceRepositoy()
    val forumCommentLiceData:ResLiveData<ForumCommentRespone> by lazy { ResLiveData()}
    fun getForumComment(id:Int,
                        start: Int,
                        pageSize: Int){
        request(
            forumCommentLiceData,
            object :BaseLiveDataCallback2<ForumCommentRespone>{}
        ){
            forumRepository.selectCommentComment(id, start, pageSize)
        }
    }
    fun addForumComment(id:Int,comment:String,pictures:List<LocalMedia>){
        viewModelScope.launch(Dispatchers.Main) {
           val map =  pictures.map {
               async {
                   ResourceRepository.upLoadIMage(it.realPath).data
               }
           }
            val urls = map.awaitAll().filterNotNull()
            Log.d("TAG", "addForumComment: ${urls}")
            request(
                forumCommentLiceData,
                object :BaseLiveDataCallback2<ForumCommentRespone>{}
            ){
                forumRepository.addCommentInForum(id,PostComment(comment,urls))
            }
        }
    }
    val previews:ResLiveData<List<Preview>> by lazy {ResLiveData()}
    fun getPreview(start:Int){
        request(
            previews,
            object :BaseLiveDataCallback2<List<Preview>>{}
        ){
            forumRepository.getThinkMap(start)
        }
    }


}
