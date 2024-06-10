package org.jxxy.debug.resources.bean

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.corekit.recyclerview.MultipleType
import org.jxxy.debug.resources.util.Mytype

class RecommendInfo : MultipleType {
    var title: String? = null
    var author: String? = null
    var authorHead: String? = null
    var content: String? = null
    var photo: String? = null
    var readHot: Int = -1
    var likes: Int = -1
    var scheme: Scheme? = null

    override fun toString(): String {
        return "RecommendInfo(title=$title, author=$author, authorHead=$authorHead, " +
            "content=$content, photo=$photo, readHot=$readHot, likes=$likes, scheme=$scheme)"
    }

    override fun viewType(): Int = Mytype.ITEM_VIDEO_RECOMMEND
}
