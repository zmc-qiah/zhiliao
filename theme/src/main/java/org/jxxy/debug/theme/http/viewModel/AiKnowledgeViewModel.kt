package org.jxxy.debug.theme.http.viewModel

import android.app.Application
import org.jxxy.debug.common.http.BaseLiveDataCallback2
import org.jxxy.debug.corekit.http.BaseViewModel
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.request
import org.jxxy.debug.theme.bean.AiKnowledgeEmbeddingsRes
import org.jxxy.debug.theme.bean.AiKnowledgeSearchEmbeddingRes
import org.jxxy.debug.theme.bean.AiKnowledgeWriteEmbeddingRes
import org.jxxy.debug.theme.http.repository.AiKnowledgeRepository

class AiKnowledgeViewModel(application: Application) : BaseViewModel(application){
    val repository : AiKnowledgeRepository by lazy {
        AiKnowledgeRepository()
    }
    val aiKnowlegdeEmbeddingsLiveData : ResLiveData<AiKnowledgeEmbeddingsRes> by lazy {
        ResLiveData()
    }

    val writeEmbeddingsLiveData : ResLiveData<AiKnowledgeWriteEmbeddingRes> by lazy {
        ResLiveData()
    }

    val searchEmbeddingLiveData : ResLiveData<AiKnowledgeSearchEmbeddingRes> by lazy {
        ResLiveData()
    }

    fun getAiKnowledgeEmbeddings(input : String){
        request(
            aiKnowlegdeEmbeddingsLiveData,
            object : BaseLiveDataCallback2<AiKnowledgeEmbeddingsRes>{}
        ){
            repository.getEmbeddings(input)
        }
    }

    fun writeEmbeddings(text : String,embeddings : List<Double>){
        request(
            writeEmbeddingsLiveData,
            object :BaseLiveDataCallback2<AiKnowledgeWriteEmbeddingRes>{}
        ){
            repository.writeEmbeddings(text,embeddings)
        }
    }

    fun searchEmbeddings(searchId : String,embeddings: List<Double>){
        request(
            searchEmbeddingLiveData,
            object : BaseLiveDataCallback2<AiKnowledgeSearchEmbeddingRes>{}
        ){
            repository.searchEmbeddings(searchId,embeddings)
        }
    }
}