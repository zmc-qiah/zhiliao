package org.jxxy.debug.resources.bean

class ChapterReview{
    var id: Int = 0
    var startOffset: Int = 0
    var endOffset: Int  = 0
    constructor(startOffset: Int,endOffset: Int){
        this.startOffset = startOffset
        this.endOffset = endOffset
    }
}