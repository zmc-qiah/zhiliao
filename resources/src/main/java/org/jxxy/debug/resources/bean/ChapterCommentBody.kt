package org.jxxy.debug.resources.bean

class ChapterCommentBody() {
    var comment: String = ""
    var tagId: Int = 0

    constructor(comment: String, tagId: Int):this() {
        this.comment = comment
        this.tagId = tagId
    }
}