package org.jxxy.debug.theme.http.resopne

import org.jxxy.debug.theme.bean.ChatChoice
import org.jxxy.debug.theme.bean.ChatUsage

class ChatRespone(
    val id: String,
    val objectValue: String,
    val created: Long,
    val model: String,
    val choices: List<ChatChoice>,
    val usage: ChatUsage
)
