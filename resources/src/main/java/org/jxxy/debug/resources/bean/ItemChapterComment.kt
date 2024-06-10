package org.jxxy.debug.resources.bean

import org.jxxy.debug.resources.util.Mytype

class ItemChapterComment(): Mytype() {
    var name: String = ""
    var picture: String = ""
    var content: String = ""
    var id: Int = 0
    constructor(name: String, picture: String, content: String) : this() {
        this.name = name
        this.picture = picture
        this.content = content
    }

    override fun viewType(): Int = ITEM_Chapter_Comment
}