package org.jxxy.debug.test.fragment.repository

import android.util.Log
import org.jxxy.debug.corekit.http.HttpManager
import org.jxxy.debug.corekit.http.TokenManager
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.test.fragment.Service.PkService
import org.jxxy.debug.test.fragment.Service.QuestionVideoService
import org.jxxy.debug.test.fragment.bean.PkMessage
import org.jxxy.debug.test.fragment.bean.PkQuestion
import org.jxxy.debug.test.fragment.bean.UserInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PkRepository {

    val value: String
        get() {
            return TokenManager.getToken().toString()
        }

    private val service: PkService by lazy {
        HttpManager.instance.service(PkService::class.java)
    }

    suspend fun joinPk() : BaseResp<UserInfo>{
        return service.joinPk(value)
    }

    suspend fun getJoin() : BaseResp<Int>{
        return service.getJoin(value)
    }

    suspend fun joinPk1() : BaseResp<Int>{
        Log.d("TAG", "joinPk1: $value")
        val retrofitBaidu = Retrofit.Builder()
            .baseUrl("http://192.168.123.194:8899/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitBaidu.create(PkService::class.java).joinPk1(value)
    }
    suspend fun getPkQuestion() : BaseResp<PkQuestion>{
        return service.getPkQuestion(value)
    }

    suspend fun postAnswerMessage(score : Int,time : Int) : BaseResp<PkMessage>{
        return service.postAnswerMessage(value,score,time)
    }

    suspend fun getAnswerMessage() : BaseResp<PkMessage>{
        return service.getAnswerMessage(value)
    }

    suspend fun finishPk() : BaseResp<Int>{
        return service.finishPk(value)
    }

}