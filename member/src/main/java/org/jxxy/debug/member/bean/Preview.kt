package org.jxxy.debug.member.bean

import org.jxxy.debug.member.util.MyType

data class Preview (val id: Int,
val name: String,
val isShared: Int,
val content: String,
val photoUrl: String
):MyType(){
    override fun viewType(): Int = PREVIEW
}