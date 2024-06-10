package org.jxxy.debug.test.fragment.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class QuestionPart(val type:Int, val content:String,val opstion:Int = -1): MultipleType {
    override fun viewType() = type
}