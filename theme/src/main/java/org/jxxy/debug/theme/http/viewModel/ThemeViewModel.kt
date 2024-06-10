package org.jxxy.debug.theme.http.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jxxy.debug.common.bean.Node
import org.jxxy.debug.common.http.BaseLiveDataCallback2
import org.jxxy.debug.corekit.http.BaseViewModel
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.request
import org.jxxy.debug.corekit.recyclerview.MultipleType
import org.jxxy.debug.theme.bean.AIBean
import org.jxxy.debug.theme.bean.ChatContent
import org.jxxy.debug.theme.bean.ChatConversation
import org.jxxy.debug.theme.bean.RespondBean
import org.jxxy.debug.theme.bean.UserInfo
import org.jxxy.debug.theme.http.repository.AiRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThemeViewModel(application: Application):BaseViewModel(application) {
    val answerLiveData by lazy {ResLiveData<String>()}
    val repository = AiRepository()
    fun chat(list: List<MultipleType>, efficientNum:Int){
        val chat = ChatConversation()
        var num = efficientNum
        chat.chatContentList = ArrayList()
        for (index in list.indices.reversed()) {
            if(list[index] is ChatContent){
                if (num-->0){
                    chat.chatContentList!!.add(list[index] as ChatContent)
                }
            }
        }
        chat.chatContentList!!.reverse()
        request(
            answerLiveData,
            object :BaseLiveDataCallback2<String>{}
        ){
            repository.chat(chat)
        }
    }
    val translateLiveData by lazy {ResLiveData<Node>()}

//    fun translate(text:String,type:Boolean){
//        request(
//            translateLiveData,
//            object :BaseLiveDataCallback2<Node>{}
//        ){
//            repository.translate(text, type)
//        }
//    }
    val baiDuTranslateLiveData by lazy {MutableLiveData<RespondBean>()}
    fun translate(text:String){
        viewModelScope.launch {
            val async = async {
                repository.translate(text)
            }
            async.await().let {
                it.enqueue(object :Callback<RespondBean>{
                    override fun onResponse(
                        call: Call<RespondBean>,
                        response: Response<RespondBean>
                    ) {
                        val respondBean = response.body()
                        respondBean?.let {
                            baiDuTranslateLiveData.postValue(it)
                        }
                    }
                    override fun onFailure(call: Call<RespondBean>, t: Throwable) {
                    }
                })
            }
        }
    }

    val userInfoLiveData:ResLiveData<List<UserInfo>> by lazy {ResLiveData()}
    fun getUserInfo(){
        request(
            userInfoLiveData,
            object : BaseLiveDataCallback2<List<UserInfo>>{}
        ){
            repository.getInfo()
        }
    }
    val themeLiveData:ResLiveData<List<AIBean>> by lazy { ResLiveData() }
    fun  getThemeAction(){
    }
}