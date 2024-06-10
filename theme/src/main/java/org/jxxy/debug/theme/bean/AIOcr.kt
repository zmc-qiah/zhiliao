package org.jxxy.debug.theme.bean

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.theme.MyType

class AIOcr(
    name: String,
    val bg:String,
    val ocr:String,
    val rePhoto:String,
    photo: String,
    scheme: Scheme?
):AIBean(name, photo, scheme,MyType.AI_OCR) {
    override fun viewType(): Int  = MyType.AI_OCR
}