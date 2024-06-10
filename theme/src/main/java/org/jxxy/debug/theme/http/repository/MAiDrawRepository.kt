package org.jxxy.debug.theme.http.repository

import org.jxxy.debug.corekit.http.HttpManager
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.theme.bean.AiDrawBodySimple
import org.jxxy.debug.theme.http.service.MAiDrawApi

class MAiDrawRepository {
    val service : MAiDrawApi by lazy {
        HttpManager.instance.service(MAiDrawApi::class.java)
    }

    suspend fun getPictureId(enhance : Int,prompt : String) : BaseResp<String>{
       return service.getPictureId(enhance, AiDrawBodySimple(prompt))
    }

    suspend fun getPictureById(id : String) : BaseResp<String>{
        return service.getPictureByID(id)
    }
}