package org.jxxy.debug.home.Fragment

//import org.jxxy.debug.home.Fragment.http.HomeFloorTypeAdapterFactory
import com.google.auto.service.AutoService
import com.google.gson.TypeAdapterFactory
import org.jxxy.debug.corekit.gson.TypeAdapterService
import org.jxxy.debug.home.Fragment.http.HomeCommonTypeAdapterFactory
import org.jxxy.debug.home.Fragment.http.service.HomeService

@AutoService(HomeService::class, TypeAdapterService::class)
class HomeServiceImpl : HomeService, TypeAdapterService {
    private val homeCommonTypeAdapterFactory by lazy { HomeCommonTypeAdapterFactory() }
    override fun registerTypeAdapterFactory(): List<TypeAdapterFactory> {
        val list = mutableListOf<TypeAdapterFactory>()
        list.add(homeCommonTypeAdapterFactory)
        return list
    }
}