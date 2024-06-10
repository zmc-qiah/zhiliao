package org.jxxy.debug.society.bean

import org.jxxy.debug.common.scheme.SchemeDetail
import org.jxxy.debug.corekit.recyclerview.MultipleType

class ExcellentIntroduceBean(val type :Int,val text :String) :
    MultipleType {
    override fun viewType(): Int {
        return type
    }
}