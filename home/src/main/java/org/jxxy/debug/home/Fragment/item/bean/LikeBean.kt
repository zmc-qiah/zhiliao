package org.jxxy.debug.home.Fragment.item.bean

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.corekit.recyclerview.MultipleType

class LikeBean(
    val resourceType:Int?=null,
    val label: String? = null,
    val title: String? = null,
    val photo: String? = null,
    val authorHead: String?=null,
    val describe:String?=null,
    val schemeDetail: Scheme? = null
): MultipleType {
    override fun viewType(): Int {
        return 7
    }

}