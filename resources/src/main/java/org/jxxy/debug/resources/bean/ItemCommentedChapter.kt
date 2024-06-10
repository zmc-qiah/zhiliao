package org.jxxy.debug.resources.bean

import org.jxxy.debug.resources.util.Mytype

class ItemCommentedChapter():Mytype(){
    var start  = 0
    var end  = 0
    var content = ""
    var id = 0
    constructor(start: Int, end: Int, id: Int) : this() {
        this.start = start
        this.end = end
        this.id = id
    }
    constructor(content: String) : this() {
        this.content = content
    }

    constructor(content: String, id: Int): this() {
        this.content = content
        this.id = id
    }


    override fun viewType(): Int = ITEM_Commented_Chapter
    override fun toString(): String {
        return "ItemCommentedChapter(start=$start, end=$end, content='$content')"
    }

    override fun equals(other: Any?): Boolean = if (other is ItemCommentedChapter) if (other.id == this.id) true else false else false
}