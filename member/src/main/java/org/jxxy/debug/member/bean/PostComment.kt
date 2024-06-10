package org.jxxy.debug.member.bean

class PostComment {
    var comment:String?=null
    var photos:List<String>?=null

    constructor(comment: String?, photos: List<String>?) {
        this.comment = comment
        this.photos = photos
    }
}