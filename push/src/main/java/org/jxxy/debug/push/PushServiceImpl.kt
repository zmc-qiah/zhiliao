package org.jxxy.debug.push

import com.google.auto.service.AutoService
import com.google.gson.TypeAdapterFactory
import org.jxxy.debug.corekit.gson.TypeAdapterService
import org.jxxy.debug.push.gson.GeTuiTypeAdapterFactory

@AutoService(TypeAdapterService::class)
class PushServiceImpl : TypeAdapterService {

    private val typeAdapterFactory by lazy { GeTuiTypeAdapterFactory() }

    override fun registerTypeAdapterFactory(): List<TypeAdapterFactory> {
        val list = mutableListOf<TypeAdapterFactory>()
        list.add(typeAdapterFactory)
        return list
    }
}
