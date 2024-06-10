package org.jxxy.debug.resources.bean

class PostComment(){
    var comment: String = ""
    var photos:List<String> = emptyList()
    constructor(comment: String) : this(){this.comment = comment}
}
