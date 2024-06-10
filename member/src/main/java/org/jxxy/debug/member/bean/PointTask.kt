package org.jxxy.debug.member.bean

import com.google.gson.annotations.SerializedName

class PointTask(
    val name: String?,
    val describe: String?,
    @SerializedName("nums")
    val currentPoint: Int?,
    @SerializedName("upperLimit")
    val maxPoint: Int?
) {
    constructor() : this(null, null, null, null)

    override fun toString(): String {
        return "PointTask(name=$name, describe=$describe, nums=$currentPoint, upperLimit=$maxPoint)"
    }
}
