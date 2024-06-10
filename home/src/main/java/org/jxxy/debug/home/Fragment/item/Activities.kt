package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.*
import org.jxxy.debug.home.Fragment.item.bean.ActivitiesBean

class Activities (type: Int?,name : String?, val activityInfo: ActivitiesBean? = null, val date:List<Int>? = null, val monthInEnglish:String? = null, val dayOfMonth:Int? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return ACTIVITIES
    }
}