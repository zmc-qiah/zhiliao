package org.jxxy.debug.theme.bean

import com.google.gson.annotations.SerializedName

data class AiKnowledgeSearchEmbeddingData(@SerializedName("_additional")val additional : AiKnowledgeSearchEmbeddingAdditional, @SerializedName("created_at")val time : String, val meta : String?, val searchable_id : String, val text : String ) {
}