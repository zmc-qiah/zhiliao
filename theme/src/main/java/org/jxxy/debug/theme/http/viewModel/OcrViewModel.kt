package org.jxxy.debug.theme.http.viewModel

import android.app.Application
import org.jxxy.debug.common.http.BaseLiveDataCallback2
import org.jxxy.debug.common.http.repository.ResourceRepository
import org.jxxy.debug.corekit.http.BaseViewModel
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.request
import org.jxxy.debug.theme.bean.OcrRes
import org.jxxy.debug.theme.bean.ReadSomethingResult
import org.jxxy.debug.theme.http.repository.OcrRepository

class OcrViewModel(application: Application) : BaseViewModel(application) {
    val repository : OcrRepository by lazy {
        OcrRepository()
    }
    val readSomethingLiveData : ResLiveData<ReadSomethingResult> by lazy {
        ResLiveData()
    }
    val turnPhotoToUrlLiveData : ResLiveData<String> by lazy {
        ResLiveData()
    }
    val ocrUseLiveData : ResLiveData<OcrRes> by lazy {
        ResLiveData()
    }

    fun readSomething(url : String,num : Int){
        request(
            readSomethingLiveData,
            object : BaseLiveDataCallback2<ReadSomethingResult>{}
        ){
            repository.readSomething(url,num)
        }
    }
    fun turnPhotoToUrl(path : String){
        request(
            turnPhotoToUrlLiveData,
            object : BaseLiveDataCallback2<String>{}
        ){
            ResourceRepository.upLoadIMage(path)
        }
    }
    fun ocrUse(url : String){
        request(
            ocrUseLiveData,
            object : BaseLiveDataCallback2<OcrRes>{}
        ){
            repository.ocrUse(url)
        }
    }
}