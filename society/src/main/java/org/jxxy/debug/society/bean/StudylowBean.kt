package org.jxxy.debug.society.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class StudylowBean (val type:Int,val titleTv :String ,val imageImg:Int): MultipleType {
    override fun viewType(): Int {
        return type
    }
}