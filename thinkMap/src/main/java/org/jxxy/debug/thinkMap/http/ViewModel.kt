package org.jxxy.debug.thinkMap.http

import android.app.Application
import org.jxxy.debug.common.bean.Resource
import org.jxxy.debug.common.http.BaseLiveDataCallback2
import org.jxxy.debug.corekit.http.BaseViewModel
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.request
import org.jxxy.debug.thinkMap.bean.ThinkMapRespone
import org.jxxy.debug.thinkMap.bean.User
import org.jxxy.debug.thinkMap.bean.UserInfo

class ViewModel(app:Application):BaseViewModel(app) {
    val re :Re by lazy { Re() }
    val isJoinLiveData :ResLiveData<List<UserInfo>> by lazy {ResLiveData()}
    fun join(userInfo: UserInfo){
        request(
            isJoinLiveData,
            object :BaseLiveDataCallback2<List<UserInfo>>{
            }
        ){
            re.joinTogether(userInfo)
        }
    }
    val thinkMapLiveData :ResLiveData<ThinkMapRespone> by lazy {ResLiveData()}
    fun getById(id:Int){
        request(
            thinkMapLiveData,
            object :BaseLiveDataCallback2<ThinkMapRespone>{
            }
        ){
            re.getById(id)
        }
    }
    fun update(body:ThinkMapRespone){
        request(
            thinkMapLiveData,
            object :BaseLiveDataCallback2<ThinkMapRespone>{
            }
        ){
            re.update(body)
        }
    }
    fun save(body:ThinkMapRespone){
        request(
            thinkMapLiveData,
            object :BaseLiveDataCallback2<ThinkMapRespone>{
            }
        ){
            re.save(body)
        }
    }
    val resourceLiveData :ResLiveData<Resource> by lazy {ResLiveData()}
    fun getInfo(id:Int){
        request(
            resourceLiveData,
            object :BaseLiveDataCallback2<Resource>{
            }
        ){
            re.getResourceInfo(id)
        }
    }
    val useLiveData :ResLiveData<User> by lazy {ResLiveData()}

    fun getUser(){
        request(
            useLiveData,
            object :BaseLiveDataCallback2<User>{
            }
        ){
            re.getInfo()
        }
    }
}