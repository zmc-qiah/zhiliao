package org.jxxy.debug.search.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class SearchEndProblem(val type: Int, val title :String, val text :String ): MultipleType {
    override fun viewType(): Int {
        return type
    }

}