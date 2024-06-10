package org.jxxy.debug.theme.bean


class ChatConversation() {
    // 以后不能这样写，List
    var chatContentList: MutableList<ChatContent> ?=null
    constructor(content: ChatContent):this(){
        chatContentList = ArrayList()
        chatContentList!!.add(content)
    }
    constructor(list: MutableList<ChatContent>):this(){
        chatContentList = list
    }
    override fun toString(): String {
        return "ChatConversation(chatContentList=$chatContentList)"
    }
}