package org.jxxy.debug.search.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class SearchEndWiki(val type:Int, val title:String, val text:String?=null) : MultipleType {
    override fun viewType(): Int {
        return type
    }}