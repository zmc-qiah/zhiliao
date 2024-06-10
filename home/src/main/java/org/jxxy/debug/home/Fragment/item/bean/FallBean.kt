package org.jxxy.debug.home.Fragment.item.bean

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.corekit.recyclerview.MultipleType

class FallBean(
    val title: String? = null,
    val type:Int = 1,
    val author: String? = null,
    val authorHead: String? = null,
    val content:String?=null,
    val photo:String?=null,
    val createTime:String?=null,
    val ip:String?=null,
    val scheme: Scheme? = null
): MultipleType {
    override fun viewType(): Int {
        return 13
    }

    override fun toString(): String {
        return "FallBean(title=$title, type=$type, author=$author, authorHead=$authorHead, content=$content, photo=$photo, createTime=$createTime, ip=$ip, scheme=$scheme)"
    }

}