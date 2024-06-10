package org.jxxy.debug.theme.bean


data class ModuleResponse(
    val moduleName: String,
    val price: Double,
    val model: String,
    val tokens: Int,
    val similarity: Double,
    val limit: Int,
    val question: String?,
    val answer: String?,
    val maxToken: Int,
    val quoteList: List<Quote>,
    val completeMessages: List<CompleteMessage>
)