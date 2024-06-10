package org.jxxy.debug.home.Fragment.item.bean

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.corekit.recyclerview.MultipleType

class GridBean  (
    val title: String? = null,
    val author: String? = null,
    val authorHead: String? = null,
    val content:String?=null,
    val photo:String?=null,
    val scheme:Scheme?=null
): MultipleType {
    override fun viewType(): Int {
        return 12
    }

    override fun toString(): String {
        return "GridBean(title=$title, author=$author, authorHead=$authorHead, content=$content, photo=$photo, scheme=$scheme)"
    }


}