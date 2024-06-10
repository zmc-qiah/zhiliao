package org.jxxy.debug.theme.bean

import com.google.gson.annotations.SerializedName

data class AiKnowledgeEmbeddingsData(
    @SerializedName("object") val type: String,
    val data: List<AiKnowledgeEmbeddingsRes>,
    val model : String,
    val usage : EmbeddingUsage
) {
}