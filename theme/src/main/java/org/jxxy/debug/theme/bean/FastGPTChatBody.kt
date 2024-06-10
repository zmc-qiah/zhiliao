package org.jxxy.debug.theme.bean

import com.debug.myapplication.http.Variables

data class FastGPTChatBody(
    val messages: List<ChatContent>,
    val stream: Boolean = true,
    val chatId: String = "undefined",
    val detail: Boolean = false,
    val variables: Variables = Variables("2022/2/2 22:22")
)