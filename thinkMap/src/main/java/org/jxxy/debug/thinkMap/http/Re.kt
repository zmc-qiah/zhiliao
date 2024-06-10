package org.jxxy.debug.thinkMap.http

import com.google.common.base.Converter
import org.jxxy.debug.common.bean.Resource
import org.jxxy.debug.corekit.gson.GsonManager.Instance.instance
import org.jxxy.debug.corekit.http.HttpManager
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.corekit.util.service
import org.jxxy.debug.thinkMap.bean.ThinkMapRespone
import org.jxxy.debug.thinkMap.bean.User
import org.jxxy.debug.thinkMap.bean.UserInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class Re {
    val apiService :Service by lazy {
        HttpManager.instance.service()
    }
    val api :Service = Retrofit.Builder()
        .baseUrl("http://47.99.43.189:8089/")
//        .baseUrl("http://192.168.246.180:8080/")
        .addConverterFactory(
            GsonConverterFactory.create(instance.gson)
        )
        .build()
        .create()
    suspend fun joinTogether(userInfo: UserInfo): BaseResp<List<UserInfo>> = api.postTogether(userInfo)
    suspend fun getById(id:Int): BaseResp<ThinkMapRespone> = apiService.getThinkMapById(id)
    suspend fun getResourceInfo(id:Int): BaseResp<Resource> = apiService.getInfo(id)
    suspend fun update(body:ThinkMapRespone):BaseResp<ThinkMapRespone> = apiService.updateMap(body)
    suspend fun save(body:ThinkMapRespone):BaseResp<ThinkMapRespone> = apiService.save(body)

    suspend fun getInfo():BaseResp<User> = apiService.getInfo()
}