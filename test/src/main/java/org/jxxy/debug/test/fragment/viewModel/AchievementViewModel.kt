package org.jxxy.debug.test.fragment.viewModel

import android.app.Application
import org.jxxy.debug.common.http.BaseLiveDataCallback2
import org.jxxy.debug.corekit.http.BaseViewModel
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.request
import org.jxxy.debug.test.fragment.bean.AchievementRes
import org.jxxy.debug.test.fragment.bean.InfoRespone
import org.jxxy.debug.test.fragment.repository.AchievementRepository

class AchievementViewModel(application: Application) : BaseViewModel(application) {
    val repository : AchievementRepository by lazy {
        AchievementRepository()
    }
    val userInfoDataLiveData: ResLiveData<InfoRespone> by lazy { ResLiveData() }
    val getAchievementLiveData : ResLiveData<AchievementRes> by lazy {
        ResLiveData()
    }

    val useAchievementLiveData : ResLiveData<Int> by lazy {
        ResLiveData()
    }
    val doneAchievementLiveData : ResLiveData<AchievementRes> by lazy {
        ResLiveData()
    }
    fun getInfo() {
        request(
            userInfoDataLiveData,
            object : BaseLiveDataCallback2<InfoRespone> {}
        ) {
            repository.getInfo()
        }
    }

    fun getAchievement(){
        request(
            getAchievementLiveData,
            object : BaseLiveDataCallback2<AchievementRes>{}
        ){
            repository.getAchievement()
        }
    }

    fun useAchievement(id : Int){
        request(
            useAchievementLiveData,
            object : BaseLiveDataCallback2<Int>{}
        ){
            repository.useAchievement(id)
        }
    }

    fun doneAchievement(ids : ArrayList<Int>){
        request(
            doneAchievementLiveData,
            object : BaseLiveDataCallback2<AchievementRes>{}
        ){
            repository.doneAchievement(ids)
        }
    }
}