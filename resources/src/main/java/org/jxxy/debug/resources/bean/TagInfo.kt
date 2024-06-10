package org.jxxy.debug.resources.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TagInfo(
    val resourceId: Int,
    val start: Int,
    val end: Int,
    val id: Int = 0,
    @SerializedName("comment")
    val content:String? = null
):Serializable