package org.jxxy.debug.theme.bean

import com.google.gson.annotations.SerializedName

data class AiKnowledgeEmbeddingsRes(
    @SerializedName("object") val type: String,
    val index: Int,
    val embedding: List<Double>
)