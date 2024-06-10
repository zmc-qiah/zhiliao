package org.jxxy.debug.common

import android.app.Application
import org.jxxy.debug.common.bean.AiBookMarkRes
import org.jxxy.debug.common.bean.BookMarkRes
import org.jxxy.debug.common.http.BaseLiveDataCallback2
import org.jxxy.debug.common.http.repository.BookMarkRepository
import org.jxxy.debug.corekit.http.BaseViewModel
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.request

class BookViewModel(application: Application) : BaseViewModel(application) {
    val repository : BookMarkRepository by lazy {
        BookMarkRepository()
    }
    val getLiveData : ResLiveData<BookMarkRes> by lazy {
        ResLiveData()
    }
    val getAiLiveData : ResLiveData<AiBookMarkRes> by lazy {
        ResLiveData()
    }

    fun getBookMarkScheme(id : Int){
        request(
            getLiveData,
            object : BaseLiveDataCallback2<BookMarkRes>{}
        ){
            repository.getBookMarkScheme(id)
        }
    }

    fun getAiBookMarkScheme(id : Int){
        request(
            getAiLiveData,
            object : BaseLiveDataCallback2<AiBookMarkRes>{}
        ){
            repository.getAiBookMarkScheme(id)
        }
    }
}