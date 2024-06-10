package org.jxxy.debug.theme.http.repository

import android.util.Log
import com.debug.myapplication.http.Variables
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.jxxy.debug.common.bean.Node
import org.jxxy.debug.common.http.repository.BaseRepository
import org.jxxy.debug.common.util.generateAnalysis
import org.jxxy.debug.common.util.getAuxiliaryWordsForNodes
import org.jxxy.debug.common.util.getJsonContent
import org.jxxy.debug.common.util.getMD5Code
import org.jxxy.debug.corekit.gson.GsonManager
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.corekit.http.interceptor.LogInterceptor
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.nullOrNot
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.theme.R
import org.jxxy.debug.theme.bean.ChatContent
import org.jxxy.debug.theme.bean.ChatConversation
import org.jxxy.debug.theme.bean.FastGPTChatBody
import org.jxxy.debug.theme.bean.RespondBean
import org.jxxy.debug.theme.bean.StreamData
import org.jxxy.debug.theme.bean.Temp
import org.jxxy.debug.theme.bean.UserInfo
import org.jxxy.debug.theme.http.service.AiApi
import org.jxxy.debug.theme.http.service.BaiduApi
import org.jxxy.debug.theme.http.service.FastGPTService
import org.jxxy.debug.theme.myListener.CommonStreamListener
import org.jxxy.debug.theme.myListener.StreamResponseListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.nio.file.attribute.AclEntryFlag
import java.util.concurrent.TimeUnit

class AiRepository:BaseRepository<AiApi>(AiApi::class.java) {
    private val gson by lazy { GsonManager.instance.gson}
    val okHttpClient by lazy { OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(LogInterceptor())
        .build()
    }
    private val  retrofit by lazy {Retrofit.Builder()
        .baseUrl("https://fastgpt.run/api/openapi/v1/")
        .addConverterFactory(GsonConverterFactory.create(GsonManager.instance.gson))
        .client(okHttpClient)
        .build() }
    private val fastGPTService by lazy {
        retrofit.create(FastGPTService::class.java)
    }
    val authorization = "Bearer ${ResourceUtil.getString(R.string.fast_gpt_apikey)}-${
        ResourceUtil.getString(
            R.string.fast_gpt_appId
        )}"
    val authorizationSecond = "Bearer ${ResourceUtil.getString(R.string.fast_gpt_apikey_second)}-${
        ResourceUtil.getString(
            R.string.fast_gpt_appId_second
        )}"

    val dsr by lazy { DashScopeRepository() }
    suspend fun chat(chatConversation: ChatConversation):BaseResp<String> = dsr.chat(
        chatConversation
    )
    suspend fun getInfo():BaseResp<List<UserInfo>> = APiService.getPicture(value)
    suspend fun translate(text:String ,type:Boolean):BaseResp<Node> {
        var map :List<Node> = emptyList()
        val root = Node()
        val res = BaseResp<Node>(0,"",root)
        val key: String = text
        coroutineScope{
            var wordList:List<String>
            if (type){
                val 文章大意 =generateAnalysis(key,"文章大意","","中文的文章大意")
                val 语法详解 =generateAnalysis(key,"语法详解","，语法详解要中英文对照要有中英文对照","语法详解,要有中英文对照")
                val 重点词汇 =generateAnalysis(key,"重点词汇","重点词汇要有中英文对照","重点词汇")
                val 全文翻译=generateAnalysis(key,"全文翻译","","中文的全文翻译")
                wordList = listOf<String>(文章大意, 语法详解,重点词汇,全文翻译)
            }else
            {
                val 词根词缀 =generateAnalysis(key,"词根词缀","同时举例有有这些词根词缀的单词或者这个词根词缀的意思，至多3个","词根词缀")
                val 例句 =generateAnalysis(key,"例句","，例句要中英文对照要有中英文对照","例句,要有中英文对照")
                val 更多 =generateAnalysis(key,"更多","","其他时态或者其他词性下的样子")
                val 相关词汇=generateAnalysis(key,"相关词汇","","的近义词和反义词")
                val 派生传记= generateAnalysis(key,"派生传记","","派生或者它是由那个词派生的")
                wordList = listOf<String>(词根词缀, 例句,更多,相关词汇,派生传记)
            }
            val list = wordList.map {
                async {
                    dsr.chat(ChatConversation(ChatContent(ChatContent.user,it)))
                }
            }
            map = list.awaitAll().map {
                var node:Node = Node()
                it.data?.let {
                    val json = getJsonContent(it)
                    node  = gson.fromJson(json, Node::class.java)
                }
                node
            }
            map.filterNotNull()
            root.text = key
            root.childNode = ArrayList(map)
        }
        return res
    }
    val retrofitBaidu by lazy{ Retrofit.Builder()
        .baseUrl("https://fanyi-api.baidu.com/api/trans/vip/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()}
    suspend fun translate(text:String):Call<RespondBean>{
        val word = text
        val from = "en"
        val to: String = "zh"
        val appid = "20230825001792997"
        val salt = (Math.random() * 100 + 1).toInt().toString()
        val key = "9XJ9q0NPxzd_vs5esm3S"
        val secretKey = "$appid$word$salt$key"
        val sign = getMD5Code(secretKey)
        val baiduTranslateService = retrofitBaidu.create(BaiduApi::class.java)
       return baiduTranslateService.translate(word, from, to, appid, salt, sign)
    }
    suspend fun empatheticResponse(text: String):BaseResp<String>{
        val auxiliary="${text}给这个情境一个情商回复,以下是一些与情商相关的回复策略和技巧:展示出真诚的兴趣或者关心，表达出你理解对方的情感，并共情，有助于建立更强的人际关系。让对方更容易理解你的感受。在情感高涨的时候，学会冷静下来，避免冲动的行为。避免情绪升级。使用非攻击性的语言，关注问题本身而不是攻击对方。"
        return dsr.chat(
            ChatConversation(ChatContent(ChatContent.user,auxiliary))
        )
    }
    suspend fun nodeNote(root:Node):Node{
        coroutineScope{
            if (root.childNode?.size?:0 >0){
                val async = async {
                    val a = getAuxiliaryWordsForNodes(root.text, "内容", "分析结果")
                    dsr.chat(
                        ChatConversation(ChatContent(ChatContent.user, a))
                    )
                }
                async.await().let {
                    it.data?.let {
                        val json = getJsonContent(it)
                        Log.d("Nodeexception", json)
                        val node  = gson.fromJson(json, Node::class.java)
                        root.childNode?.add(node)
                    }
                }
            }else{
                async{
                    val async = root.childNode?.map{
                        async {
                            val a = getAuxiliaryWordsForNodes(root.text, it.text, it.text)
                            dsr.chat(
                                ChatConversation(ChatContent(ChatContent.user, a))
                            )
                        }
                    }
                    async?.awaitAll()?.let{
                        root.childNode = ArrayList()
                        it.forEach{
                            it.data?.let {
                                val json = getJsonContent(it)
                                Log.d("Nodeexception", json)
                                val node  = gson.fromJson(json, Node::class.java)
                                root.childNode?.add(node)
                            }
                        }
                    }
                }
            }
        }
        return root
    }
    suspend fun study(text: String):Node{
    val node =Node(text)
    val listOf = listOf<Temp>(
        Temp(
            "学习大纲",
            "学习大纲（Curriculum）是一个学习计划的总览或概要，它列出了学习过程中要涵盖的主题、内容、技能和目标。大纲通常是一个指导性的文档，用于指导学习者在特定主题或课程中的学习路径和进程。在教育和培训领域，制定一个清晰明确的学习大纲对于有效的学习和教学至关重要。学习大纲通常包括以下要素：课程或主题名称：大纲会明确指出所涵盖的课程、课程部分或主题的名称。学习目标：大纲会列出学习者在学完课程或主题后应该具备的知识、技能和能力。这些目标可以是具体的、可测量的成果。内容概述：大纲会提供一个关于将要学习的内容的概述。这通常是一份详细的主题清单，涵盖课程的各个部分。课程结构：大纲会描述课程的组织和结构，包括分为哪些模块、单元或章节。它可能还会提供每个部分的时长和重点。教学方法和资源：大纲可能会介绍教学方法、学习资源和工具，以支持学生的学习过程。评估和考核：大纲通常会说明学生将如何被评估和考核，以确定他们是否达到了学习目标。推荐学习前置条件：有时，大纲可能会提供在开始学习之前需要具备的先前知识、技能或背景。学习大纲的目的是为学生、教师和其他教育工作者提供一个指导，以确保在学习过程中有系统、有组织地掌握所需的知识和技能。它还有助于保持课程的一致性和质量，确保教学目标得以实现"
        ),
        Temp(
            "学习计划",
            "学习计划（Study Plan）是一份详细规划，描述了学习者在一段时间内如何分配时间和资源来达到学习目标的计划。学习计划通常是一个个人或团体在学习过程中制定的路线图，旨在帮助他们有条不紊地组织学习活动，掌握所需的知识和技能。一个有效的学习计划通常包括以下要素：学习目标：学习计划的首要任务是明确学习者希望达到的目标。这些目标可以是短期的，如学会某项技能，也可以是长期的，如完成一门课程或获得某种资格证书。学习内容：学习计划会列出学习者需要掌握的知识和技能内容。这可以是一本教材、课程大纲、在线资源等。时间分配：学习计划会规定学习者在每天、每周或每月内分配多少时间来学习。时间分配应该合理，兼顾其他活动和休息时间。学习活动：学习计划会指定具体的学习活动，如阅读教材、观看教学视频、练习习题等。这有助于保持学习的多样性和活跃性。里程碑和截止日期：学习计划可能会设定一些里程碑和截止日期，以便学习者在特定时间内完成特定任务或达到一定的学习阶段。反馈和评估：学习计划可能会包含自我评估或反馈的部分，以便学习者了解自己的进展，并根据需要进行调整。应对挑战：学习计划可能会考虑一些潜在的挑战或障碍，如时间不足、难题等，并提供应对策略。灵活性：一个好的学习计划应该具有一定的灵活性，以适应不可预测的变化或突发情况。学习计划帮助学习者有组织地管理学习时间，避免拖延，提高学习效率。它还可以帮助学习者保持动力，追求目标，并在学习过程中更加有目的性地前进。"
        ),
        Temp(
            "学习资源",
            "学习资源是用于获取知识、信息和技能的工具、材料和途径。在学习过程中，合适的学习资源可以帮助学习者更好地理解和掌握所学内容。学习资源的种类多样，以下是一些常见的学习资源类型：教材和课本：学科教材和课本是学习的主要来源之一，提供了系统化的知识和概念介绍。在线课程和教程：在线平台上有许多免费或付费的课程，提供了视频、文本、测验等多种形式的学习材料。教学视频：视频课程、讲座和教学视频可以直观地展示概念和操作，适用于视觉学习者。网络资源：网络上有丰富的学习资源，如博客、维基百科、学术文章、论坛等，提供了深入的知识和观点。练习题和习题集：练习题和习题集可以帮助巩固学习内容，提供实际操作的机会。模拟实验和实践项目：对于实践性学科，模拟实验和实践项目可以让学习者实际操作，获得经验。应用程序和软件：学习类应用程序和软件可以提供交互性学习体验，让学习更加生动。社交媒体和社区：在社交媒体和在线社区中，学习者可以与其他人交流、讨论，分享经验和资源。图书馆和实体资源：图书馆是获取书籍、期刊、报纸等实体资源的好地方，适用于需要深入研究的学习者。导师和教师：从经验丰富的导师或教师那里获取指导和建议是一种重要的学习资源。在线工具和计算器：对于一些学科，如数学和科学，在线计算器和工具可以辅助学习。多媒体素材：图片、音频、动画等多媒体素材可以增强学习体验，使概念更具体易懂。学习资源的选择应根据学习者的学习风格、目标和需求来定。多样性的学习资源可以帮助学习者从不同角度理解和掌握知识，增强学习效果。"
        )
    )
    coroutineScope {
        val async = listOf.map{
            async {
                val a = get("我想要学习${text}请分析他的${it.key}", text, it.key)
                dsr.chat(
                    ChatConversation(ChatContent(ChatContent.user, a))
                )
            }
        }
        var cnt = 0
        async.awaitAll().let{
            node.childNode = ArrayList()
            it.forEach{
                it.data?.let {
                    val json = getJsonContent(it)
                    try {
                        val node1  = gson.fromJson(json, Node::class.java)
                        if (node1 != null){
                            node.childNode?.add(node1)
                        }else{
                            node.childNode?.add(
                                Node().apply {
                                    title = listOf[cnt].key
                                    this.text = it
                                }
                            )
                        }
                    }catch (e: JsonSyntaxException){
                        node.childNode?.add(
                            Node().apply {
                                title = listOf[cnt].key
                                this.text = it
                            }
                        )
                    }
                }
                cnt++
            }
        }
    }
    return node
}
    fun get(subject: String, mainBody: String,nodeName:String = "",more:String = ""): String {
        val template = "我想要学习${subject},你可以给我一个${mainBody}吗？" +
                "以下面类对应的json的形式返回,严格按照要求的格式,你只需要回复json数据" +
                "class Node():"+
                "    var title: String = \"\"\n" +
                "    var childNode: MutableList<Node> ?= null"
                "}" +
                "根节点text为“$nodeName”${more}"+
                "尽量简短,严格按照上面要求的格式"
        return template
    }
}
data class a(val s:String,val flag: Boolean)