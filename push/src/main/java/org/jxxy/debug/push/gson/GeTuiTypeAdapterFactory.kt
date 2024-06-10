package org.jxxy.debug.push.gson

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import org.jxxy.debug.corekit.gson.CommonTypeAdapter
import org.jxxy.debug.push.card.castToTarget

class GeTuiTypeAdapterFactory : TypeAdapterFactory {

    override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        if (GeTuiBaseBean::class.java.isAssignableFrom(type.rawType)) {
            val adapter = CommonTypeAdapter<GeTuiBaseBean>(gson, this, "type")
            // 注册各种类型
            adapter.addSubTypeAdapter<PushCardDefaultEntity>(PUSH_CARD_DEFAULT)
            return adapter.castToTarget()
        }
        return null
    }
}
