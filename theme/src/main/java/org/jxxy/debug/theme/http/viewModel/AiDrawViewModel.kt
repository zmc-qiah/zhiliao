package org.jxxy.debug.theme.http.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jxxy.debug.common.http.BaseLiveDataCallback2
import org.jxxy.debug.corekit.http.BaseViewModel
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.corekit.http.bean.ErrorResponse
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.exceptionHandler
import org.jxxy.debug.corekit.http.listener.LiveDataCallback
import org.jxxy.debug.corekit.http.process
import org.jxxy.debug.corekit.http.request
import org.jxxy.debug.corekit.util.nullOrNot
import org.jxxy.debug.theme.bean.*
import org.jxxy.debug.theme.http.repository.AiDrawRepository

class AiDrawViewModel(application: Application) : BaseViewModel(application) {
    val repository : AiDrawRepository by lazy {
        AiDrawRepository()
    }
    val getPictureIdLiveData : ResLiveData<AiDrawRes> by lazy {
        ResLiveData()
    }

    val getPictureByIdLiveData : ResLiveData<AiDrawPictureRes> by lazy {
        ResLiveData()
    }

    fun getPictureId(prompt: String){
        request(
            getPictureIdLiveData,
            object : BaseLiveDataCallback2<AiDrawRes>{}
        ){
            repository.getPictureId(prompt)
        }
    }

    fun getPictureById(id : String){
        request(
            getPictureByIdLiveData,
            object : BaseLiveDataCallback2<AiDrawPictureRes>{}
        ){
            repository.getPictureById(id)
        }
    }

    inline fun <T, D> request(
        resLiveData: ResLiveData<T>,
        callback: LiveDataCallback<T, D>,
        crossinline block: suspend () -> AiDrawResp<D>?
    ) {
        viewModelScope.launch(exceptionHandler(resLiveData, callback) + Dispatchers.IO) {
            val response = async {
                block.invoke()
            }
            response.await().nullOrNot({
                callback.error(resLiveData, ErrorResponse.analysisError())
            }) {
                it.process(resLiveData, callback)
            }
        }
    }

}