package org.jxxy.debug.society.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class CreatorBean (val type:Int,val titleTv :String ,val authoridTv:String,val imageImg:Int): MultipleType {
    override fun viewType(): Int {
        return type
    }
}