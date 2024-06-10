package org.jxxy.debug.test.fragment.repository

import org.jxxy.debug.corekit.http.HttpManager
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.test.fragment.Service.AchievementService
import org.jxxy.debug.test.fragment.bean.AchievementRes
import org.jxxy.debug.test.fragment.bean.InfoRespone

class AchievementRepository {
    val service : AchievementService by lazy {
        HttpManager.instance.service(AchievementService::class.java)
    }

    suspend fun getAchievement() : BaseResp<AchievementRes>{
        return service.getAchievement()
    }

    suspend fun useAchievement(id : Int) : BaseResp<Int>{
        return service.useAchievement(id)
    }

    suspend fun doneAchievement(ids : ArrayList<Int>) : BaseResp<AchievementRes>{
        return service.doneAchievement(ids)
    }

    suspend fun getInfo(): BaseResp<InfoRespone> {
        return service.getInfo()
    }
}