package org.jxxy.debug.society.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class ExpfinshBean(val type :Int, val text :String, val image :String, val time:String,val authorhead :String,val nameTv:String,val yearTv:String,val monthTv:String,val dayTv:String) :
    MultipleType {
    override fun viewType(): Int {
        return type
    }
}