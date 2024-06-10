package org.jxxy.debug.resources.http.repository

import android.util.Log
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.jxxy.debug.common.bean.Node
import org.jxxy.debug.common.util.getJsonContent
import org.jxxy.debug.corekit.gson.GsonManager
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.corekit.http.interceptor.LogInterceptor
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.nullOrNot
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.resources.R
import org.jxxy.debug.resources.bean.ChatContent
import org.jxxy.debug.resources.bean.FastGPTChatBody
import org.jxxy.debug.resources.http.service.AiApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.util.concurrent.TimeUnit

class AiRepository:BaseRepository<AiApi>(AiApi::class.java) {
//    private val gson by lazy { GsonManager.instance.gson}
//    val okHttpClient by lazy { OkHttpClient.Builder()
//        .connectTimeout(60, TimeUnit.SECONDS)
//        .readTimeout(60, TimeUnit.SECONDS)
//        .writeTimeout(60, TimeUnit.SECONDS)
//        .addInterceptor(LogInterceptor())
//        .build()
//    }
//    private val  retrofit by lazy {
//        Retrofit.Builder()
//        .baseUrl("https://fastgpt.run/api/openapi/v1/")
//        .addConverterFactory(GsonConverterFactory.create(GsonManager.instance.gson))
//        .client(okHttpClient)
//        .build() }
//    private val fastGPTService by lazy {
//        retrofit.create(AiApi::class.java)
//    }
//    val authorizationSecond = "Bearer ${ResourceUtil.getString(R.string.fast_gpt_apikey_second)}-${
//        ResourceUtil.getString(
//            R.string.fast_gpt_appId_second
//        )}"
    suspend fun AIAnalysisTabulation(text: String):BaseResp<Node> {
        val res =APiService.onceChat(value,
            text+"分析这篇文章我希望你给出树状图的结构，以json的形式" +
                    "class Node(): Serializable {" +
                    "    var text: String      var childNode: MutableList<Node> ?= null}根节点的text是什么？他有多少次级结点,只回复json的内容就行，别的不用")
        var respone = BaseResp<Node>(res.code,res.message)
        res.data?.let {
            val json = getJsonContent(it)
            val gson = Gson()
            val node = gson.fromJson(json, Node::class.java)
            Log.d("TAG", "AIAnalysisTabulation: ${node}")
            respone = BaseResp<Node>(res.code,res.message,node)
        }
        return respone
    }
//    fun streamFastChatSecond(chatConversation: ChatConversation,listener:StreamResponseListener){
//        Log.d("chatConversation","$chatConversation")
//        chatConversation.chatContentList.nullOrNot({"对话数据为空".toast()},{
//            fastGPTService.streamChat(FastGPTChatBody(it),authorizationSecond).enqueue(
//                object : Callback<ResponseBody> {
//                    override fun onResponse(
//                        call: Call<ResponseBody>,
//                        response: Response<ResponseBody>
//                    ) {
//                        if (response.isSuccessful && response.body() != null) {
//                            val responseBody = response.body()!!.byteStream()
//                            val reader = responseBody.reader()
//                            try {
//                                val bufferedReader = BufferedReader(reader)
//                                var line :String?=bufferedReader.readLine()
//                                while (line!= null) {
//                                    if (line!!.contains("data: [DONE]")){
//                                        listener.isEnd()
//                                    }else{
//                                        val content = getJsonContent(line!!)
//                                        if (content.length>2){
//                                            val fromJson = gson.fromJson(content,StreamData::class.java)
//                                            listener.onStreamDataReceived(fromJson)
//                                        }
//                                    }
//                                    line =bufferedReader.readLine()
//                                }
//                            } catch (e: IOException) {
//                                // 处理读取数据时可能发生的异常
//                                e.printStackTrace()
//                            } finally {
//                                reader?.close()
//                            }
//                        }
//                        else {
//                            // 处理请求失败的情况
//                            // 例如，打印错误信息
//                            println("Request failed with code: ${response.code()}")
//                        }
//                    }
//                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                        listener.onStreamError(t)
//                    }
//
//                }
//            )
//        })
//    }
}
//class ChatConversation() {
//    // 以后不能这样写，List
//    var chatContentList: MutableList<ChatContent> ?=null
//    constructor(content: ChatContent):this(){
//        chatContentList = ArrayList()
//        chatContentList!!.add(content)
//    }
//    constructor(list: MutableList<ChatContent>):this(){
//        chatContentList = list
//    }
//    override fun toString(): String {
//        return "ChatConversation(chatContentList=$chatContentList)"
//    }
//}
//interface StreamResponseListener {
//    fun onStreamDataReceived(data: StreamData)
//    fun onStreamError(error: Throwable)
//    fun isEnd()
//}
//data class StreamData(
//    val id: String,
//    val `object`: String,
//    val created: Long,
//    val choices: List<StreamItem>
//)
//data class StreamItem(
//    val delta: Delta,
//    val index: Int,
//    val finish_reason: String?
//)
//data class Delta(
//    val content: String?
//)

