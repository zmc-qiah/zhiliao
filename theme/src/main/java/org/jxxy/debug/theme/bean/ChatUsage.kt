package org.jxxy.debug.theme.bean


class ChatUsage(
    val promptTokens: Int,
    val completionTokens: Int,
    val totalTokens: Int,
    val preTokenCount: Int,
    val preTotal: Int,
    val adjustTotal: Int,
    val finalTotal: Int
)