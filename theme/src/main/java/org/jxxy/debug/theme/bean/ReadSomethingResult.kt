package org.jxxy.debug.theme.bean

import com.google.gson.annotations.SerializedName

data class ReadSomethingResult(
    @SerializedName("result_num") val num: Long,
    @SerializedName("result")val result: List<ReadSomethingInnerResult>,
    @SerializedName("log_id") val id: Long,
) {
}