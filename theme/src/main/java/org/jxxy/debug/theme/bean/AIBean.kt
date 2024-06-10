package org.jxxy.debug.theme.bean

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.theme.MyType
import org.jxxy.debug.theme.MyType.Companion.AI_PAINT
import org.opencv.photo.Photo

open class AIBean(val name: String, val photo: String, val scheme: Scheme?, val type: Int):MyType{
    override fun viewType(): Int = type
}