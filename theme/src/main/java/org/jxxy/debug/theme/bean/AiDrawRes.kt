package org.jxxy.debug.theme.bean

data class AiDrawRes(
    val estimate: Long,
    val estimates: List<Long>,
    val id: String,
    val ids: List<String>
) {
}