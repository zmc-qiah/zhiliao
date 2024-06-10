package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.home.Fragment.http.response.CommonBean

class Interview (
    type: Int?,name : String?,
    val authorName:String?=null,
    val authorJob:String?=null,
    val authorPoint:String?=null,
    val videoTitle:String?=null,
    val videoUrl:String?=null,
    val videoPhoto:String?=null,
    val scheme: Scheme?=null
) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return INTERVIEW
    }
}
