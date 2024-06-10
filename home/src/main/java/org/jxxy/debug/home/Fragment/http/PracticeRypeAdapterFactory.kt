package org.jxxy.debug.home.Fragment.http

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import org.jxxy.debug.corekit.gson.CommonTypeAdapter
import org.jxxy.debug.home.Fragment.http.response.Practice
import org.jxxy.debug.home.Fragment.http.response.Study
import org.jxxy.debug.home.Fragment.item.*
import org.jxxy.debug.home.Fragment.item.bean.Carousell

//class PracticeRypeAdapterFactory : TypeAdapterFactory {
//    override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
//        if (Practice::class.java.isAssignableFrom(type.rawType)) {
//            val adapter = CommonTypeAdapter<Practice>(gson, this, "type")
//            // 注册各种类型
////            adapter.addSubTypeAdapter<Carousell>(Practice.CAROUSEL)
////            adapter.addSubTypeAdapter<Notice>(Practice.NOTICE)
////            adapter.addSubTypeAdapter<Record>(Practice.RECORD)
////            adapter.addSubTypeAdapter<Activities>(Practice.ACTIVITIES)
////            adapter.addSubTypeAdapter<Competition>(Practice.COMPETITION)
////            adapter.addSubTypeAdapter<Advertise>(Practice.ADVERTISE)
////            adapter.addSubTypeAdapter<Discovery>(Practice.DISCOVERY)
////            adapter.addSubTypeAdapter<Theme>(Practice.THEME)
////            adapter.addSubTypeAdapter<Posture>(Practice.POSTURE)
////            adapter.addSubTypeAdapter<Share>(Practice.SHARE)
////            adapter.addSubTypeAdapter<Come>(Practice.COME)
//            return adapter as (TypeAdapter<T>)
//        }
//        return null
//    }
//}
