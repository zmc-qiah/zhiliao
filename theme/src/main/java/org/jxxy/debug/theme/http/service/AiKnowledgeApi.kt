package org.jxxy.debug.theme.http.service

import org.jxxy.debug.theme.bean.*
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AiKnowledgeApi {
    @POST("v1/embeddings")
    suspend fun getEmbeddings(
        @Header("Content-Type") type: String,
        @Header("Authorization") authorization: String,
        @Body body: AiKnowledgeEmbeddingsBody,
    ): AiKnowledgeEmbeddingsData

    @POST("vector")
    suspend fun writeEmbeddings(
        @Header("Content-Type") type: String,
        @Header("Authorization") authorization: String,
        @Body body: AiKnowledgeWriteEmbeddingBody
    ) : AiKnowledgeWriteEmbeddingRes

    @POST("vector/search")
    suspend fun searchEmbeddings(
        @Header("Content-Type") type: String,
        @Header("Authorization") authorization: String,
        @Body body: AiKnowledgeSearchEmbeddingBody
    ) : AiKnowledgeSearchEmbeddingRes


}