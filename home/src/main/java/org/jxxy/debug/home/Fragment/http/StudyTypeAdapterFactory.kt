package org.jxxy.debug.home.Fragment.http

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import org.jxxy.debug.corekit.gson.CommonTypeAdapter
import org.jxxy.debug.home.Fragment.http.response.Study
import org.jxxy.debug.home.Fragment.item.*

class StudyTypeAdapterFactory : TypeAdapterFactory {

    override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        if (Study::class.java.isAssignableFrom(type.rawType)) {
            val adapter = CommonTypeAdapter<Study>(gson, this, "type")

            // 注册各种类型

            return adapter as (TypeAdapter<T>)
        }

        return null
    }
}
