package org.jxxy.debug.society.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class QuestionBean(val type:Int ,val authorImg:String,val authoridTv:String,val titleTv:String,val tagTv:String,val questionTv:String) :
    MultipleType {
    override fun viewType(): Int {
        return type
    }
}