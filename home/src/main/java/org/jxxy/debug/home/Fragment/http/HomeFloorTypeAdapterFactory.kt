package org.jxxy.debug.home.Fragment.http

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import org.jxxy.debug.corekit.gson.CommonTypeAdapter
import org.jxxy.debug.home.Fragment.http.response.Component
import org.jxxy.debug.home.Fragment.http.response.Practice
import org.jxxy.debug.home.Fragment.http.response.Study
import org.jxxy.debug.home.Fragment.item.*

//class HomeFloorTypeAdapterFactory: TypeAdapterFactory {
//
//    override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
//        if (Component::class.java.isAssignableFrom(type.rawType)) {
//            val adapter = CommonTypeAdapter<Component>(gson, this, "type")
//
//            // 注册各种类型
//            adapter.addSubTypeAdapter<Carousel>(Component.CAROUSEL)
//            adapter.addSubTypeAdapter<HotTopic>(Component.HOTTOPIC)
//            adapter.addSubTypeAdapter<Like>(Component.LIKE)
//            adapter.addSubTypeAdapter<Vote>(Component.VOTE)
//            adapter.addSubTypeAdapter<Lastview>(Component.LAST)
//            adapter.addSubTypeAdapter<Expert>(Component.EXPERT)
//            adapter.addSubTypeAdapter<News>(Component.NEWS)
//            adapter.addSubTypeAdapter<Law>(Component.LAW)
//            adapter.addSubTypeAdapter<History>(Component.History)
//            adapter.addSubTypeAdapter<GridFour>(Component.GRIDFOUR)
//            adapter.addSubTypeAdapter<Flash>(Component.FLASH)
//            adapter.addSubTypeAdapter<Fall>(Component.FALL)
//            adapter.addSubTypeAdapter<Grid>(Component.GRID)
//            return adapter as (TypeAdapter<T>)
//        }
//
//        return null
//    }
//}
