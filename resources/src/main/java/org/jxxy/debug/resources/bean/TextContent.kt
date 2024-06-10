package org.jxxy.debug.resources.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType
import org.jxxy.debug.resources.util.Mytype

class TextContent(
    val content: String,
    val id:Int,
    val start:Int,
    val tagInfo: List<TagInfo>?,
    val baseOffset:Int) : MultipleType {
    override fun viewType(): Int = Mytype.ITEM_TEXT_CONTENE
}
