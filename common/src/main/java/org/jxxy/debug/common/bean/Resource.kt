package org.jxxy.debug.common.bean

import com.google.gson.annotations.SerializedName
import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.corekit.recyclerview.MultipleType

class Resource(
    val createTime: String? = null,
    @SerializedName("describe")
    val resourceDescribe: String? = null,
    @SerializedName("likes")
    val resourceLikes: Long? = null,
    @SerializedName("photo")
    val resourcePhoto: String? = null,
    @SerializedName("readHot")
    val resourceReads: Long? = null,
    @SerializedName("title")
    val resourceTitle: String? = null,
    @SerializedName("author")
    val resourceAuthorName: String? = null,
    @SerializedName("authorHead")
    val resourceAuthorHead: String? = null,
    // '类型 1文章，2图片，3视频',
    @SerializedName("type")
    val resourceType: Int = -1,
    val scheme: Scheme? = null
) : MultipleType {
    val resourceId: Int
        get() {return scheme?.resourceId ?: 1}
    override fun viewType(): Int = resourceType
    constructor() : this(null)
}
