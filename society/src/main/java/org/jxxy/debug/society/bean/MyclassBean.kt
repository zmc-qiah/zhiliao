package org.jxxy.debug.society.bean

import org.jxxy.debug.common.scheme.SchemeDetail
import org.jxxy.debug.corekit.recyclerview.MultipleType

class MyclassBean  (val type :Int, val textTv :String, val image :String, val progressTv :String,val scheme :SchemeDetail) :
    MultipleType {
    override fun viewType(): Int {
        return type
    }
}