package org.jxxy.debug.theme.bean

data class EmbeddingUsage(
    val prompt_tokens: Int,
    val total_tokens: Int,
    val pre_token_count: Int,
    val pre_total: Int,
    val adjust_total: Int,
    val final_total: Int
) {
}