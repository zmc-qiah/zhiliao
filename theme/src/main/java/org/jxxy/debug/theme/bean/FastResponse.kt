package org.jxxy.debug.theme.bean

data class FastResponse(
    val responseData: List<ModuleResponse>,
    val id: String,
    val model: String,
    val usage: Usage,
    val choices: List<Choice>
)