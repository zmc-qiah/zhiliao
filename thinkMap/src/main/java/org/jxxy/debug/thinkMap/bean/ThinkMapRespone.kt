package org.jxxy.debug.thinkMap.bean

data class ThinkMapRespone(val id:Int,
                           val name:String,
                           val isShared:Int = 0,
                           val jsonText: String,
                           val content: String,
                           val photo: String)