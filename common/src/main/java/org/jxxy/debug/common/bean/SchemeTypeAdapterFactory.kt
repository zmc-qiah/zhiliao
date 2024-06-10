package org.jxxy.debug.common.bean

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import org.jxxy.debug.common.scheme.*
import org.jxxy.debug.corekit.gson.CommonTypeAdapter

class SchemeTypeAdapterFactory : TypeAdapterFactory {

    override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        if (Scheme::class.java.isAssignableFrom(type.rawType)) {
            val adapter = CommonTypeAdapter<Scheme>(gson, this, "type")
            // 注册各种类型
            adapter.addSubTypeAdapter<SchemeDetail>(Scheme.DETAIL)
            adapter.addSubTypeAdapter<SchemeH5>(Scheme.H5)
            adapter.addSubTypeAdapter<SchemeSearch>(Scheme.SEARCH)
            adapter.addSubTypeAdapter<Scheme>(Scheme.Society)
            adapter.addSubTypeAdapter<Scheme>(Scheme.Plan)
            adapter.addSubTypeAdapter<Scheme>(Scheme.Video)
            adapter.addSubTypeAdapter<Scheme>(Scheme.Text)
            adapter.addSubTypeAdapter<Scheme>(Scheme.PAINT)
            adapter.addSubTypeAdapter<SchemeGame>(Scheme.GAME)
            adapter.addSubTypeAdapter<Scheme>(10)
            adapter.addSubTypeAdapter<SchemeAI>(Scheme.AI)
            return adapter as (TypeAdapter<T>)
        }

        return null
    }
}
