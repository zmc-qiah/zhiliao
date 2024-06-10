package org.jxxy.debug.member.http.repository

import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.member.bean.MemberGroup
import org.jxxy.debug.member.bean.TaskRespone
import org.jxxy.debug.member.http.respone.PointDetailRespone
import org.jxxy.debug.member.http.service.PointAPI

class PointRepository : BaseRepository<PointAPI>(PointAPI::class.java) {
    suspend fun getTask(): BaseResp<TaskRespone> {
        return APiService.getTask(value)
    }
    suspend fun getRank(): BaseResp<MemberGroup> {
        return APiService.getRank(value)
    }
    suspend fun getDetail(): BaseResp<PointDetailRespone> { return APiService.getDetail(value)
    }
}
