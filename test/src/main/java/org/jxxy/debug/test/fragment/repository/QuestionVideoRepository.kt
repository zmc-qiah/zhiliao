package org.jxxy.debug.test.fragment.repository

import org.jxxy.debug.corekit.http.HttpManager
import org.jxxy.debug.corekit.http.TokenManager
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.test.fragment.Service.QuestionVideoService
import org.jxxy.debug.test.fragment.Service.TestService
import org.jxxy.debug.test.fragment.bean.QuestionVideo
import org.jxxy.debug.test.fragment.bean.QuestionVideoInner
import org.jxxy.debug.test.fragment.bean.QuestionVideoList

class QuestionVideoRepository {
    val value: String
        get() {
            return TokenManager.getToken().toString()
        }
    private val service: QuestionVideoService by lazy {
        HttpManager.instance.service(QuestionVideoService::class.java)
    }

    suspend fun getQuestionVideoList() : BaseResp<QuestionVideoList>{
        return service.getQuestionVideoList(value)
    }

    suspend fun getQuestionVideoById(id : Int) : BaseResp<QuestionVideoInner>{
        return service.getQuestionVideoById(value,id)
    }
}