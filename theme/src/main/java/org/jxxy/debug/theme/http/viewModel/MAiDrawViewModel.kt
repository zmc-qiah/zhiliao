package org.jxxy.debug.theme.http.viewModel

import android.app.Application
import org.jxxy.debug.common.http.BaseLiveDataCallback2
import org.jxxy.debug.corekit.http.BaseViewModel
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.request
import org.jxxy.debug.theme.http.repository.MAiDrawRepository

class MAiDrawViewModel(application: Application) : BaseViewModel(application) {
    val repository: MAiDrawRepository by lazy {
        MAiDrawRepository()
    }
    val getPictureIdLiveData: ResLiveData<String> by lazy {
        ResLiveData()
    }

    val getPictureByIdLiveData: ResLiveData<String> by lazy {
        ResLiveData()
    }

    fun getPictureId(enhance: Int, prompt: String) {
        request(
            getPictureIdLiveData,
            object : BaseLiveDataCallback2<String> {}
        ) {
            repository.getPictureId(enhance, prompt)
        }
    }

    fun getPictureById(id: String) {
        request(
            getPictureByIdLiveData,
            object : BaseLiveDataCallback2<String> {}
        ) {
            repository.getPictureById(id)
        }
    }
}