package org.jxxy.debug.home.Fragment.http.repository

import org.jxxy.debug.corekit.http.HttpManager
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.corekit.util.service
import org.jxxy.debug.home.Fragment.http.response.*
import org.jxxy.debug.home.Fragment.http.service.HomeApi
import org.jxxy.debug.home.Fragment.item.Fall
import org.jxxy.debug.home.Fragment.item.StudyFloor

class HomeRepository {
    private val service:HomeApi by lazy{
        HttpManager.instance.service()
    }

    suspend fun getHomeFloor():BaseResp<HomeFloor>{
        return service.getHomeFloor()
    }

    suspend fun getFalls(start:Int,pageSize:Int): BaseResp<Fall> {
        return service.getFalls(start, pageSize)
    }

    suspend fun getStudyFloor(): BaseResp<StudyFloor> {
        return service.getStudyFloor()
    }

    suspend fun getPracticeFloor(): BaseResp<PracticeFloor> {
        return service.getPracticeFloor()
    }
    suspend fun getTab():BaseResp<TabInfosRespone> = service.getTab()
    suspend fun getHome(type: Int):BaseResp<CommonRespone> =service.getHome(type)
    suspend fun getIdentity(identityId:Int):BaseResp<String> =service.getIdentity(identityId)
}