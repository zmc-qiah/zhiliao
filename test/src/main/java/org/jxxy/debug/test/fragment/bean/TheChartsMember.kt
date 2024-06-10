package org.jxxy.debug.test.fragment.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class TheChartsMember(val member : Int,val url:String,val name:String,val Damage:String,val type: Int = 1) : MultipleType{
    override fun viewType(): Int = type
}