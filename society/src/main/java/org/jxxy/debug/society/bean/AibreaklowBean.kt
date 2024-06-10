package org.jxxy.debug.society.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class AibreaklowBean(val type:Int,val titleTv :String ,val imageId:Int): MultipleType {
    override fun viewType(): Int {
        return type
    }
}