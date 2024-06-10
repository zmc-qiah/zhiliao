package org.jxxy.debug.test.fragment.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class TestPart(val type:Int,val picSrc:Int = 0,val content: String = "") : MultipleType {
    override fun viewType() = type
}