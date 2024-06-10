package org.jxxy.debug.member.http.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.member.bean.LoginData
import org.jxxy.debug.member.bean.Plan
import org.jxxy.debug.member.bean.PostPlan
import org.jxxy.debug.member.http.respone.FollowRespone
import org.jxxy.debug.member.http.respone.GroupForumResponse
import org.jxxy.debug.member.http.respone.HistoryRespone
import org.jxxy.debug.member.http.respone.InfoRespone
import org.jxxy.debug.member.http.respone.MarkRespone
import org.jxxy.debug.member.http.respone.MemberRespone
import org.jxxy.debug.member.http.respone.NoteRespone
import org.jxxy.debug.member.http.respone.WeekReportResponce
import org.jxxy.debug.member.http.service.MemberApi

class MemberRepository : BaseRepository<MemberApi>(MemberApi::class.java) {
//    var value: String = PersistenceUtil.getValue<String>("satoken") ?: "error"
//    private val APiService: MemberApi by lazy {
//        HttpManager.instance.service()
//    }
    suspend fun getMember(): BaseResp<MemberRespone> {
        return APiService.getMember(value)
    }
    suspend fun getMemberFellow(): BaseResp<FollowRespone> {
        return APiService.getMemberFellow(value)
    }
    suspend fun getMemberNote(): BaseResp<NoteRespone> {
        return APiService.getMemberNote(value)
    }
    suspend fun getMemberHistory(): BaseResp<HistoryRespone> {
        return APiService.getMemberHistory(value)
    }
    suspend fun getMemberMark(): BaseResp<MarkRespone> {
        return APiService.getMemberMark(value)
    }
    suspend fun getMemberFlan(): BaseResp<ArrayList<Plan>> {
        var respone = BaseResp<ArrayList<Plan>>()
        coroutineScope {
            respone = async(Dispatchers.IO) {
                APiService.getMemberPlan(value)
            }.await()
            val plans = respone.data
            plans?.let {
                val map = it.map {
                    async(Dispatchers.IO) {
                        it.studyEvent = APiService.getMemberEvent(value, it.planId.toInt()).data
                    }
                }
                map.awaitAll()
            }
        }
        return respone
    }
    suspend fun login(): BaseResp<LoginData> {
        return APiService.login("15990771449", "123456")
    }
    suspend fun logout(): BaseResp<Any> {
        return APiService.logout(value)
    }
    suspend fun addPlan(plan: Plan): BaseResp<Int?> {
        val events = PostPlan(plan.studyEvent)
        Log.d("TAG", "addPlan: " + events)
        Log.d("TAG", "addPlan:2 " + plan.studyEvent)
        return APiService.postPlan(
            value,
            plan.name!!,
            plan.startTime!!,
            plan.endTime!!,
            events
        )
    }
    suspend fun getInfo(): BaseResp<InfoRespone> {
        return APiService.getInfo(value)
    }
    suspend fun getForum(start: Int, pageSize: Int): BaseResp<GroupForumResponse> {
        return APiService.getForum(value, start, pageSize)
    }
    suspend fun getReport(): BaseResp<WeekReportResponce> = APiService.getReport(value)
}
