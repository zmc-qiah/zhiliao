package org.jxxy.debug.theme.myListener

import org.jxxy.debug.theme.bean.ChatContent

interface ChatItemClick {
    fun onClick(viewType: String,entity:ChatContent,postion:Int)
}