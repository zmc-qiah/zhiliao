package org.jxxy.debug.theme.bean

data class StreamData(
    val id: String,
    val `object`: String,
    val created: Long,
    val choices: List<StreamItem>
)
data class StreamItem(
    val delta: Delta,
    val index: Int,
    val finish_reason: String?
)
data class Delta(
    val content: String?
)