package org.jxxy.debug.society.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class AnswerBean(val type :Int,val text :String,val image :String,val answernameTv:String) : MultipleType {
    override fun viewType(): Int {
        return type
    }
}