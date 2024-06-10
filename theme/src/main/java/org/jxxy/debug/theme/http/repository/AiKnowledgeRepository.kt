package org.jxxy.debug.theme.http.repository

import android.util.Log
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.theme.bean.*
import org.jxxy.debug.theme.http.service.AiKnowledgeApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AiKnowledgeRepository {
    val service: AiKnowledgeApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://oa.api2d.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(AiKnowledgeApi::class.java)
    }
    val type = "application/json"
    val authorization = "Bearer fk213310-8AdUDoJBZoIKFXnzfG6cdWQYiq22xjAA"

    suspend fun getEmbeddings(input: String): BaseResp<AiKnowledgeEmbeddingsRes> {
        var res : AiKnowledgeEmbeddingsRes? = null
        coroutineScope {
            val datas = async {
                service.getEmbeddings(
                    type,
                    authorization,
                    AiKnowledgeEmbeddingsBody(input = input)
                )
            }.await()
            Log.d("Embeddings的数据","${datas}")
            datas?.let {
                Log.d("Embeddings的数据","${it.data[0]}")
                res = it.data[0]
            }
        }
        Log.d("Embeddings","我进入了")
        Log.d("EmbeddingsRes的数据","$res")
        return BaseResp(0,"", res)
    }

    suspend fun writeEmbeddings(text : String,embeddings : List<Double>,uuid : String = "65ddf4f5-77f2-4d28-a0b5-20b300154b21") : BaseResp<AiKnowledgeWriteEmbeddingRes>{
        var res : AiKnowledgeWriteEmbeddingRes? = null
        coroutineScope {
            val datas = async {
                service.writeEmbeddings(
                    type,
                    authorization,
                    AiKnowledgeWriteEmbeddingBody(text,embeddings,uuid)
                )
            }.await()
            datas?.let {
                res = it
            }
        }
        Log.d("uuid","${res?.uuid}")
        return BaseResp(0,"", res)
    }

    suspend fun searchEmbeddings(searchID : String,embeddings: List<Double>) : BaseResp<AiKnowledgeSearchEmbeddingRes>{
        var res : AiKnowledgeSearchEmbeddingRes? = null
        coroutineScope {
            val datas = async {
                service.searchEmbeddings(
                    type,
                    authorization,
                    AiKnowledgeSearchEmbeddingBody(searchID, embeddings)
                )
            }.await()
            datas?.let {
                res = it
            }
        }
        return BaseResp(0,"", res)
    }
}