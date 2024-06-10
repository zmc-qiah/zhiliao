package org.jxxy.debug.home.Fragment.http

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import org.jxxy.debug.corekit.gson.CommonTypeAdapter
import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.*
import org.jxxy.debug.home.Fragment.item.bean.Carousell

class HomeCommonTypeAdapterFactory: TypeAdapterFactory {
    override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        if (CommonBean::class.java.isAssignableFrom(type.rawType)) {
            val adapter = CommonTypeAdapter<CommonBean>(gson, this, "type")
            // 注册各种类型
            adapter.addSubTypeAdapter<Carousell>(CommonBean.CAROUSEL)
            adapter.addSubTypeAdapter<HotTopic>(CommonBean.HOTTOPIC)
            adapter.addSubTypeAdapter<Like>(CommonBean.LIKE)
            adapter.addSubTypeAdapter<Vote>(CommonBean.VOTE)
            adapter.addSubTypeAdapter<Lastview>(CommonBean.LAST)
            adapter.addSubTypeAdapter<Expert>(CommonBean.EXPERT)
            adapter.addSubTypeAdapter<News>(CommonBean.NEWS)
            adapter.addSubTypeAdapter<Law>(CommonBean.LAW)
            adapter.addSubTypeAdapter<History>(CommonBean.History)
            adapter.addSubTypeAdapter<GridFour>(CommonBean.GRIDFOUR)
            adapter.addSubTypeAdapter<Flash>(CommonBean.FLASH)
            adapter.addSubTypeAdapter<Fall>(CommonBean.FALL)
            adapter.addSubTypeAdapter<Grid>(CommonBean.GRID)
            adapter.addSubTypeAdapter<Carousell>(CommonBean.CAROUSEL_3)
            adapter.addSubTypeAdapter<Notice>(CommonBean.NOTICE)
            adapter.addSubTypeAdapter<Record>(CommonBean.RECORD)
            adapter.addSubTypeAdapter<Activities>(CommonBean.ACTIVITIES)
            adapter.addSubTypeAdapter<Competition>(CommonBean.COMPETITION)
            adapter.addSubTypeAdapter<Advertise>(CommonBean.ADVERTISE)
            adapter.addSubTypeAdapter<Discovery>(CommonBean.DISCOVERY)
            adapter.addSubTypeAdapter<Theme>(CommonBean.THEME)
            adapter.addSubTypeAdapter<Posture>(CommonBean.POSTURE)
            adapter.addSubTypeAdapter<Share>(CommonBean.SHARE)
            adapter.addSubTypeAdapter<Come>(CommonBean.COME)
            adapter.addSubTypeAdapter<Daily>(CommonBean.DAILY)
            adapter.addSubTypeAdapter<Tools>(CommonBean.TOOLS)
            adapter.addSubTypeAdapter<Course>(CommonBean.COURSE)
            adapter.addSubTypeAdapter<Book>(CommonBean.BOOK)
            adapter.addSubTypeAdapter<Interview>(CommonBean.INTERVIEW)
            adapter.addSubTypeAdapter<Technology>(CommonBean.TECHNOLOGY)
            adapter.addSubTypeAdapter<Learn>(CommonBean.LEARN)
            adapter.addSubTypeAdapter<Breakthough>(CommonBean.BREAKTHOUGH)
            adapter.addSubTypeAdapter<Read>(CommonBean.READ)
            adapter.addSubTypeAdapter<Ted>(CommonBean.TED)
            adapter.addSubTypeAdapter<Popularize>(CommonBean.POPULARIZE)
            adapter.addSubTypeAdapter<Study_2>(CommonBean.LEARN_2)
            adapter.addSubTypeAdapter<org.jxxy.debug.home.Fragment.item.Practice>(CommonBean.PRACTICE)
            return adapter as (TypeAdapter<T>)
        }
        return null
    }
}