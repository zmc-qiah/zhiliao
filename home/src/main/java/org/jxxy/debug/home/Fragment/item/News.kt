package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.NewsBean

class News(type: Int?,name : String?, val newInfos: List<NewsBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return NEWS
    }
}
