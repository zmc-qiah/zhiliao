package org.jxxy.debug.search.bean

import org.jxxy.debug.common.scheme.SchemeDetail
import org.jxxy.debug.corekit.recyclerview.MultipleType

class SearchEndNotebook(val type: Int, val imageId1:String, val imageId2:String, val imageId3:String, val imageId4:String, val imageId5:String) : MultipleType {
    override fun viewType(): Int {
        return type
    }

}