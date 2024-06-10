package org.jxxy.debug.member.bean

import com.google.gson.annotations.SerializedName

data class Follow(
    val id: Int ? = null,
    @SerializedName("followerName")
    val name: String? = null,
    @SerializedName("headPhoto")
    val photo: String? = null,
    @SerializedName("followerDescribe")
    val describe: String? = null,
    val creatTime: String? = null
)
