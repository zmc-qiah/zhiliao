package org.jxxy.debug.society.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class ExptodayBean (val type :Int,val time:String) :
    MultipleType {
    override fun viewType(): Int {
        return type
    }
}