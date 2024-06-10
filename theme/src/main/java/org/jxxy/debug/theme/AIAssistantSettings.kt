package org.jxxy.debug.theme

import android.util.Log
import com.iflytek.cloud.SpeechConstant
import org.jxxy.debug.theme.bean.ChatContent

object AIAssistantSettings {
    // 发音人
    var voicer = "xiaoyan"
    // 音速
    var voiceSpeed = 50
    // 语调
    var voicePitch= 50
    // 音量
    var voiceVolume = 50
    //
    var isBreakOnOtherVoice = true
    // 语音听写语言
    var TatLanguage = "zh_cn"
    //
    var tatEngineType = SpeechConstant.TYPE_CLOUD
    // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
    var tatVadbosPreference = 4000
    var tatVadeosPreference = 1000
    // 对话次数
    var chatCnt = 3
    val musicPlayCommands = listOf(
        "播放音乐",
        "播放一段音乐",
        "听首歌",
        "来点音乐。",
        "我要听",
        "我想听",
        "打开音乐"
    )
    val emoCommands = listOf(
        "什么表情",
        "什么情绪",
        "情绪是什么",
        "表情是什么",
        "表情是怎么样",
        "情绪是怎么样",
        "表情怎么样",
        "情绪怎么样",
    )
    val planCommands = listOf(
        "学习计划"
    )
    const val Angry = 0
    const val Happy = 1
    const val Neutral = 2
    const val Sad = 3
    const val Surprise = 4
    var CurrentEMOTendencies = Neutral
    val EMOPromote = ChatContent(ChatContent.user,"请注意，我现在的情绪是（描述你的情绪，例如：有些焦虑、沮丧、兴奋等）。在回答问题时，请考虑我的情绪。")
    fun getEMO(int: Int): String = when(int){
        0->"生气"
        1->"高兴"
        3->"伤心"
        4->"惊讶"
        else ->{
            Log.d("TAG", "getEMO: ${int}")
            "面无表情"
        }
    }
    fun setEmo(string: String) = when(true){
        string.contains("angry")-> CurrentEMOTendencies = 0
        string.contains("happy")->CurrentEMOTendencies =1
        string.contains("surprise")->CurrentEMOTendencies =4
        string.contains("sad")->CurrentEMOTendencies =3
        else -> {
            Log.d("TAG", "setEmo: ${string}")
            CurrentEMOTendencies =2
        }
    }
}