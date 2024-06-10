package org.jxxy.debug.test.fragment.viewModel

import android.app.Application
import org.jxxy.debug.common.http.BaseLiveDataCallback2
import org.jxxy.debug.corekit.http.BaseViewModel
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.listener.LiveDataCallback
import org.jxxy.debug.corekit.http.request
import org.jxxy.debug.test.fragment.bean.PkMessage
import org.jxxy.debug.test.fragment.bean.PkQuestion
import org.jxxy.debug.test.fragment.bean.UserInfo
import org.jxxy.debug.test.fragment.repository.PkRepository
import org.jxxy.debug.test.fragment.repository.QuestionVideoRepository

class PkViewModel(application: Application) : BaseViewModel(application) {
    val repository: PkRepository by lazy {
        PkRepository()
    }

    val joinPkLiveData: ResLiveData<UserInfo> by lazy {
        ResLiveData()
    }

    val getJoinLiveData: ResLiveData<Int> by lazy {
        ResLiveData()
    }

    val getPkQuestion: ResLiveData<PkQuestion> by lazy {
        ResLiveData()
    }

    val postAnswerMessage: ResLiveData<PkMessage> by lazy {
        ResLiveData()
    }

    val getAnswerMessage: ResLiveData<PkMessage> by lazy {
        ResLiveData()
    }

    val finishJoinLiveData: ResLiveData<Int> by lazy {
        ResLiveData()
    }

    fun joinPk() {
        request(
            joinPkLiveData,
            object : BaseLiveDataCallback2<UserInfo> {}
        ) {
            repository.joinPk()
        }
    }

    fun getJoin() {
        request(
            getJoinLiveData,
            object : BaseLiveDataCallback2<Int> {}
        ) {
            repository.getJoin()
        }
    }

    fun getPkQuestion() {
        request(
            getPkQuestion,
            object : BaseLiveDataCallback2<PkQuestion> {}
        ) {
            repository.getPkQuestion()
        }
    }

    fun postAnswerMessage(score: Int, time: Int) {
        request(
            postAnswerMessage,
            object : BaseLiveDataCallback2<PkMessage> {}
        ) {
            repository.postAnswerMessage(score, time)
        }
    }

    fun getAnswerMessage(){
        request(
            getAnswerMessage,
            object : BaseLiveDataCallback2<PkMessage>{}
        ){
            repository.getAnswerMessage()
        }
    }

    fun finishPk(){
        request(
            finishJoinLiveData,
            object : BaseLiveDataCallback2<Int>{}
        ){
            repository.finishPk()
        }
    }
}