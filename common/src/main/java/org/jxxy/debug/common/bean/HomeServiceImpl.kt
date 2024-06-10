package org.jxxy.debug.common.bean

import com.google.auto.service.AutoService
import com.google.gson.TypeAdapterFactory
import org.jxxy.debug.corekit.gson.TypeAdapterService

@AutoService(TypeAdapterService::class)
class CommonServiceImpl :  TypeAdapterService {

    override fun registerTypeAdapterFactory(): List<TypeAdapterFactory> {
        val list = mutableListOf<TypeAdapterFactory>()
        list.add(SchemeTypeAdapterFactory())
        return list
    }
}