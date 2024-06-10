package org.jxxy.debug.common.bean

import com.luck.picture.lib.entity.LocalMedia
import java.io.Serializable

class Node(): Serializable {
    var text: String = ""
    var title: String = ""
    var description: String
        get() = text
        set(value) {
            text =value
        }
    var url: String
        get() = text
        set(value) {
            text =value
        }
    var childNode: MutableList<Node> ?= null
    @Transient
    var list: List<LocalMedia> ?= null
    var pictureList: MutableList<String> ?= null
    var resource:Resource?=null
    var type:Int = 0
    var id:Int = 0

    constructor(text: String) : this() {
        this.title = text
    }
    constructor(text: String,id:Int) : this() {
        this.title = text
        this.id = id
    }
    constructor(childNode: List<Node>,nodeText: String) : this() {
        this.title = nodeText
        this.childNode =ArrayList(childNode)
    }
    constructor(text: String,pictureList: MutableList<String>) : this() {
        this.title = text
        this.pictureList = pictureList
    }

    override fun toString(): String {
        return "Node(text='$text', title='$title', childNode=$childNode, list=$list, pictureList=$pictureList, resource=$resource, type=$type, id=$id)"
    }

}
