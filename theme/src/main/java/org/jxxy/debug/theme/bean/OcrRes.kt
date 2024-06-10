package org.jxxy.debug.theme.bean

import com.google.gson.annotations.SerializedName

data class OcrRes(
    @SerializedName("words_result") val words: List<OcrWords>,
    @SerializedName("words_result_num") val num: Int,
    @SerializedName("log_id")val id : String
) {
}