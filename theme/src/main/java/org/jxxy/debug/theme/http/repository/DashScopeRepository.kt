package org.jxxy.debug.theme.http.repository

import android.util.Log
import com.alibaba.dashscope.aigc.generation.Generation
import com.alibaba.dashscope.aigc.generation.GenerationParam
import com.alibaba.dashscope.aigc.generation.GenerationResult
import com.alibaba.dashscope.common.Message
import com.alibaba.dashscope.common.Role
import com.alibaba.dashscope.exception.ApiException
import com.alibaba.dashscope.exception.InputRequiredException
import com.alibaba.dashscope.exception.NoApiKeyException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jxxy.debug.corekit.gson.GsonManager
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.theme.R
import org.jxxy.debug.theme.bean.ChatContent
import org.jxxy.debug.theme.bean.ChatConversation

class DashScopeRepository {
    private val gson by lazy { GsonManager.instance.gson}
    suspend fun chat(chatConversation: ChatConversation): BaseResp<String> = withContext(Dispatchers.IO){
        if ( chatConversation.chatContentList == null )
            BaseResp<String>(1,"lise == null","")
        else{
            val messages = chatConversation.chatContentList!!.map {
                createMessage(
                    if (it.role == ChatContent.user) Role.USER
                    else Role.SYSTEM,
                    it.content
                )
            }
            val param = createGenerationParam(messages)
            val result = callGenerationWithMessages(param)
            val answer  = result.output.choices[0].message.content
            BaseResp<String>(0,"",answer)
        }
    }

    fun createGenerationParam(messages: List<Message>?): GenerationParam {
        return GenerationParam.builder()
            .model(ResourceUtil.getString(R.string.dash_model))
            .messages(messages)
            .apiKey(ResourceUtil.getString(R.string.dash_key))
            .resultFormat(GenerationParam.ResultFormat.MESSAGE)
            .topP(0.8)
            .build()
    }
    private fun createMessage(role: Role, content: String): Message {
        return Message.builder().role(role.value).content(content).build()
    }

    @Throws(ApiException::class, NoApiKeyException::class, InputRequiredException::class)
    fun callGenerationWithMessages(param: GenerationParam?): GenerationResult {
        val gen = Generation()
        return gen.call(param)
    }

}