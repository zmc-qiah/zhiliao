package org.jxxy.debug.home.Fragment

import com.google.gson.TypeAdapterFactory
import org.jxxy.debug.corekit.gson.TypeAdapterService
import com.google.auto.service.AutoService
import org.jxxy.debug.home.Fragment.http.HomeCommonTypeAdapterFactory
//import org.jxxy.debug.home.Fragment.http.HomeFloorTypeAdapterFactory
import org.jxxy.debug.home.Fragment.http.StudyTypeAdapterFactory
import org.jxxy.debug.home.Fragment.http.response.Practice
import org.jxxy.debug.home.Fragment.http.service.HomeService

@AutoService(HomeService::class, TypeAdapterService::class)
class HomeServiceImpl : HomeService, TypeAdapterService {

//    private val homeFloorTypeAdapterFactory by lazy { HomeFloorTypeAdapterFactory() }
//    private val studyTypeAdapterFactory by lazy { StudyTypeAdapterFactory() }
//    private val practiceTypeAdapterFactory by lazy { PracticeRypeAdapterFactory() }
    private val homeCommonTypeAdapterFactory by lazy { HomeCommonTypeAdapterFactory() }
    override fun registerTypeAdapterFactory(): List<TypeAdapterFactory> {
        val list = mutableListOf<TypeAdapterFactory>()
        list.add(homeCommonTypeAdapterFactory)
        return list
    }
}