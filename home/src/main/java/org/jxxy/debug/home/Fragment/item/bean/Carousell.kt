package org.jxxy.debug.home.Fragment.item.bean

import org.jxxy.debug.home.Fragment.http.response.CommonBean

class Carousell (type: Int?,name : String?, val carousel: List<CarouselBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return CAROUSEL_3
    }
}
