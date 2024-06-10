package org.jxxy.debug.member.bean

import com.google.gson.annotations.SerializedName
import org.jxxy.debug.corekit.recyclerview.MultipleType
import java.io.Serializable

class Forum() :Serializable,MultipleType{
    var comment: String? = null
    val createTime: String ? = null
    val headPhoto: String? = null
    val resourceId: Int? = null
    val resourcePhoto: String? = null
    val resourceTitle: String? = null
    val userName: String? = null
    var commentId: Int = 0
    var groupId: Int = 0
    @SerializedName("commentPhotos")
    var commentPhotos: List<String> ?=null
    var comments: List<Forum>? = null
    var commentLikes: Int = 0
    var commentNum: Int = 0
    var type = 1
    var flag = false
    override fun viewType(): Int = type
}
