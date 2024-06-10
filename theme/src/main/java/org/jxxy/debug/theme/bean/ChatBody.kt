package org.jxxy.debug.theme.bean

class ChatBody() {
    val model:String="gpt-3.5-turbo"
    var messages : List<ChatContent> ?=null
    val safe_mode = false
    constructor(chatConversation: ChatConversation):this(){
        messages = chatConversation.chatContentList
    }
}