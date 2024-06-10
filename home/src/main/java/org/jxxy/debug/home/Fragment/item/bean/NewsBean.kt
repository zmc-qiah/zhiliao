package org.jxxy.debug.home.Fragment.item.bean

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.corekit.recyclerview.MultipleType

class NewsBean(
    val title: String? = null,
    val photo: String? = null,
    val author: String?=null,
    val authorHead:String?=null,
    val readHot:Int?=null,
    val scheme: Scheme? = null
): MultipleType {

    override fun viewType(): Int {
        return 8
    }

    override fun toString(): String {
        return "NewsBean(title=$title, photo=$photo, author=$author, authorHead=$authorHead, readHot=$readHot, scheme=$scheme)"
    }

}