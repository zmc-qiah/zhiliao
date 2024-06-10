package org.jxxy.debug.search.bean

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.common.scheme.SchemeDetail
import org.jxxy.debug.corekit.recyclerview.MultipleType

class SearchEndBook(val type: Int, val text: String, val imageID: String, val text2: String, val schme: Scheme) : MultipleType {
    override fun viewType(): Int {
       return type
    }
}