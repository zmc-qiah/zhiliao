package org.jxxy.debug.test.fragment.viewModel

import android.app.Application
import org.jxxy.debug.common.http.BaseLiveDataCallback2
import org.jxxy.debug.corekit.http.BaseViewModel
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.request
import org.jxxy.debug.test.fragment.bean.QuestionVideoInner
import org.jxxy.debug.test.fragment.bean.QuestionVideoList
import org.jxxy.debug.test.fragment.repository.QuestionRepository
import org.jxxy.debug.test.fragment.repository.QuestionVideoRepository

class QuestionVideoViewModel(application: Application) : BaseViewModel(application) {
    val repository: QuestionVideoRepository by lazy {
        QuestionVideoRepository()
    }
    val questionvideoListLiveData: ResLiveData<QuestionVideoList> by lazy {
        ResLiveData()
    }
    val questionVideoByIdLiveData: ResLiveData<QuestionVideoInner> by lazy {
        ResLiveData()
    }
    var winUrl : String = ""
    var loseUrl : String = ""

    fun getQuestionVideoList() {
        request(
            questionvideoListLiveData,
            object : BaseLiveDataCallback2<QuestionVideoList> {}
        ) {
            repository.getQuestionVideoList()
        }
    }

    fun getQuestionVideoById(id : Int) {
        request(
            questionVideoByIdLiveData,
            object : BaseLiveDataCallback2<QuestionVideoInner> {},
        ){
            repository.getQuestionVideoById(id)
        }
    }

}