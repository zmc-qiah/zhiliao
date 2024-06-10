package org.jxxy.debug.home.Fragment.http.viewmodel

import android.app.Application
import android.util.Log
import org.jxxy.debug.common.http.BaseLiveDataCallback2
import org.jxxy.debug.corekit.http.BaseViewModel
import org.jxxy.debug.corekit.http.bean.ErrorResponse
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.listener.LiveDataCallback
import org.jxxy.debug.corekit.http.request
import org.jxxy.debug.home.Fragment.http.repository.HomeRepository
import org.jxxy.debug.home.Fragment.http.response.*
import org.jxxy.debug.home.Fragment.item.Fall
import org.jxxy.debug.home.Fragment.item.StudyFloor
import java.lang.Exception

class HomeViewModel(application: Application) : BaseViewModel(application) {
    private val repository:HomeRepository by lazy{
        HomeRepository()
    }

    val liveData:ResLiveData<HomeFloor> by lazy{
        ResLiveData()
    }

    val FallLiveData:ResLiveData<Fall> by lazy{
        ResLiveData()
    }

    val StudyLiveData:ResLiveData<StudyFloor> by lazy{
        ResLiveData()
    }

    val PracticeLiveData:ResLiveData<PracticeFloor> by lazy{
        ResLiveData()
    }

    val IdentityNameLiveData:ResLiveData<String> by lazy{
        ResLiveData()
    }

    fun getHomeFloor() {
        val TAG = "confine"
        Log.d(TAG, "HomeViewModel")
        request(
            liveData,
            object : LiveDataCallback<HomeFloor, HomeFloor> {
                override fun error(emit: ResLiveData<HomeFloor>, e: ErrorResponse) {
                }

                override fun otherCode(
                    emit: ResLiveData<HomeFloor>,
                    code: Int?,
                    msg: String?,
                    data: HomeFloor?
                ) {
                }

                override fun success(emit: ResLiveData<HomeFloor>, msg: String?, data: HomeFloor?) {
                    data?.let { emit.success(it) }
                }
            }
        ) {
            repository.getHomeFloor()
        }
    }
    fun getFalls(nowPage: Int = 1, size: Int = 10) {
        request(
            FallLiveData,
            object : LiveDataCallback<Fall,Fall> {
                override fun error(emit: ResLiveData<Fall>, e: ErrorResponse) {
                }

                override fun otherCode(
                    emit: ResLiveData<Fall>,
                    code: Int?,
                    msg: String?,
                    data: Fall?
                ) {
                }

                override fun success(emit: ResLiveData<Fall>, msg: String?, data: Fall?) {
                    data?.let { emit.success(it) }
                }
            }
        ) {
            repository.getFalls(nowPage,size)
        }
    }

    fun getStudyFloor() {
        request(
            StudyLiveData,
            object : LiveDataCallback<StudyFloor, StudyFloor> {
                override fun error(emit: ResLiveData<StudyFloor>, e: ErrorResponse) {
                }

                override fun otherCode(
                    emit: ResLiveData<StudyFloor>,
                    code: Int?,
                    msg: String?,
                    data: StudyFloor?
                ) {
                }

                override fun success(emit: ResLiveData<StudyFloor>, msg: String?, data: StudyFloor?) {
                    data?.let { emit.success(it) }
                }
            }
        ) {
            repository.getStudyFloor()
        }
    }

    fun getPracticeFloor() {
        request(
            PracticeLiveData,
            object : LiveDataCallback<PracticeFloor, PracticeFloor> {
                override fun error(emit: ResLiveData<PracticeFloor>, e: ErrorResponse) {
                }

                override fun otherCode(
                    emit: ResLiveData<PracticeFloor>,
                    code: Int?,
                    msg: String?,
                    data: PracticeFloor?
                ) {
                }

                override fun success(emit: ResLiveData<PracticeFloor>, msg: String?, data: PracticeFloor?) {
                    data?.let { emit.success(it) }
                }
            }
        ) {
            repository.getPracticeFloor()
        }
    }
    val tabLiveData by lazy {ResLiveData<TabInfosRespone>()}
    fun getTab(){
        request(
            tabLiveData,
            object :BaseLiveDataCallback2<TabInfosRespone>{})
            { repository.getTab() }
    }
    val homeLiveData by lazy {ResLiveData<CommonRespone>()}
    fun getHome(type:Int){
        request(
            homeLiveData,
            object :BaseLiveDataCallback2<CommonRespone>{})
        { repository.getHome(type) }
    }

    fun getIdentity(identityId:Int){
        try {
            request(
                IdentityNameLiveData,
                object :BaseLiveDataCallback2<String>{})
            {
                repository.getIdentity(identityId)
            }
        }catch (e:Exception){
            Log.d("test", "getIdentity: ${e.printStackTrace()} $e")
        }

    }

}