package org.jxxy.debug.member.bean

import com.google.gson.annotations.SerializedName

data class PointDetailGroup(
    val todayGetSum: Int ? = null,
    val createTime: String? = null,
    @SerializedName("task")
    val list: List<PointDetailChild>
)
