package org.jxxy.debug.resources.bean

data class FastGPTChatBody(
    val messages: List<ChatContent>,
    val stream: Boolean = true,
    val chatId: String = "undefined",
    val detail: Boolean = false,
    val variables: Variables = Variables("2022/2/2 22:22")
)
data class Variables(
    val cTime: String
)
class ChatContent() {
    var role: String = ""
    var content: String = ""
}