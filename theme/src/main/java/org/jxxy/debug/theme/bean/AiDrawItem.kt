package org.jxxy.debug.theme.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class AiDrawItem(val type : Int) : MultipleType {
    override fun viewType(): Int {
        return type
    }
}