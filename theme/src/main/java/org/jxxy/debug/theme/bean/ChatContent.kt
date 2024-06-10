package org.jxxy.debug.theme.bean

import org.jxxy.debug.theme.MyType
import org.jxxy.debug.theme.MyType.Companion.CHAT_ASSISTANT
import org.jxxy.debug.theme.MyType.Companion.CHAT_TRANSLATE
import org.jxxy.debug.theme.MyType.Companion.CHAT_USER

class ChatContent():MyType {
     var role: String = ""
     var content: String = ""
    var picture:String? = null
        get(){return when(this.role) {
            assistant -> assistantPicture
            user -> userPicture
            else -> null
        }}

    constructor(role: String, content: String):this() {
        this.role = role
        this.content = content
    }

    companion object{
        val assistant = "assistant"
        val user = "user"
        val translate= "baidu"
        var assistantPicture:String ?= null
        var userPicture:String ?= null
    }

    override fun toString(): String {
        return "ChatContent(role='$role', content='$content')"
    }

    override fun viewType(): Int = when(role) {
        user ->CHAT_USER
        assistant -> CHAT_ASSISTANT
        translate -> CHAT_TRANSLATE
        else -> -1
    }

}
