package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.CarouselBean

class Carousel(type: Int?,name : String?, val carousel: List<CarouselBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return CAROUSEL
    }
}
