package org.jxxy.debug.activity

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.debug.myapplication.http.Variables
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.jxxy.debug.common.util.getJsonContent
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.gson.GsonManager
import org.jxxy.debug.corekit.http.HttpManager
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.service
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.databinding.ActivityTestBinding
import org.jxxy.debug.theme.R
import org.jxxy.debug.theme.bean.ChatContent
import org.jxxy.debug.theme.bean.ChatConversation
import org.jxxy.debug.theme.bean.FastGPTChatBody
import org.jxxy.debug.theme.bean.StreamData
import org.jxxy.debug.theme.http.repository.AiRepository
import org.jxxy.debug.theme.http.service.FastGPTService
import org.jxxy.debug.theme.myListener.StreamResponseListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import kotlin.math.log

class TextActivity:BaseActivity<ActivityTestBinding>() {
    val re = AiRepository()
    val se = HttpManager.instance.service<FastGPTService>()
    val authorizationSecond = "Bearer ${ResourceUtil.getString(R.string.fast_gpt_apikey_second)}-${
        ResourceUtil.getString(
            R.string.fast_gpt_appId_second
        )}"
    val  gson = GsonManager.instance.gson
    override fun bindLayout(): ActivityTestBinding = ActivityTestBinding.inflate(layoutInflater)

    override fun initView() {
//        lifecycleScope.launch{
//            re.streamChat(
//                ChatConversation(ArrayList<ChatContent>().apply { add(ChatContent(ChatContent.user,"说一段40个字的文章")) })
//            ).collect {
//                val a = it.s
//                val b = it.flag
//                if (b){
//                    Log.d("Stream的数据2", "initView: $a")
//                }
//            }
//        }
//        se.streamChat(
//            FastGPTChatBody(
//                ArrayList<ChatContent>().apply { add(ChatContent(ChatContent.user,"说一段40个字的文章")) },
//                true,
//                "undefined ",
//                false,
//                Variables("${System.currentTimeMillis()}")
//            ),
//            authorizationSecond
//        ).enqueue(object :Callback<ResponseBody>{
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                if (response.isSuccessful && response.body() != null) {
//                    val responseBody = response.body()!!.byteStream()
//                    val reader = responseBody.reader()
//                    try {
//                        val bufferedReader = BufferedReader(reader)
//                        var line :String?=bufferedReader.readLine()
//                        while (line!= null) {
//                            if (line!!.contains("data: [DONE]")){
//                            }else{
//                                val content = getJsonContent(line!!)
//                                if (content.length>2){
//                                    val fromJson = gson.fromJson(content,StreamData::class.java)
//                                    Log.d("aaResponseBody", "onResponse: ${fromJson.choices[0].delta}")
//                                }
//                            }
//                            line =bufferedReader.readLine()
//                        }
//                    } catch (e: IOException) {
//                        // 处理读取数据时可能发生的异常
//                        e.printStackTrace()
//                    } finally {
//                        reader?.close()
//                    }
//                }
//
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//            }
//
//        })
////        requestChat(ArrayList<ChatContent>().apply { add(ChatContent(ChatContent.user,"说一段40个字的文章")) })
    }
    fun requestChat(list : ArrayList<ChatContent>){
//        lifecycleScope.launch {
//            var nowString : String = ""
//            re.streamFastChatSecond(ChatConversation(list),object :
//                StreamResponseListener {
//                override fun onStreamDataReceived(data: StreamData) {
//                    Log.d("Stream的数据1", "onStreamDataReceived: ${data}")
//                    if(data.choices?.size?:0>0){
//                        data.choices[0].delta.content?.let {
//                            Log.d("Stream的数据2", "onStreamDataReceived:${it}")
//                            if (it.length>0){
//                                nowString += it
//                            }
//                        }
//                    }
//                }
//                override fun onStreamError(error: Throwable) {
//                }
//                override fun isEnd() {
//                    "$nowString".toast()
//                    Log.d("Stream的数据2", "isEnd: $nowString")
////                        adapter.add(ChatContent(ChatContent.assistant,nowString))
////                        view.loadingAnimation.stopAnim()
////                        view.loadingAnimation.gone()
////                        view.submitIcon.show()
////                        view.chatRV.smoothScrollToPosition(adapter.itemCount - 1)
//                }
//            })
//        }
    }

    override fun subscribeUi() {
    }
}