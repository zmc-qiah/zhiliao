package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.CourseBean

class Course (type: Int?,name : String?, val classInfos: List<CourseBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return COURSE
    }
}
