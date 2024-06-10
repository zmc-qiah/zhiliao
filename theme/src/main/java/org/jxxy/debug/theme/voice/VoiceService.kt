package org.jxxy.debug.theme.voice

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context.WINDOW_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.iflytek.cloud.ErrorCode
import com.iflytek.cloud.InitListener
import com.iflytek.cloud.RecognizerListener
import com.iflytek.cloud.RecognizerResult
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechError
import com.iflytek.cloud.SpeechEvent
import com.iflytek.cloud.SpeechRecognizer
import com.iflytek.cloud.SpeechSynthesizer
import com.iflytek.cloud.SpeechUtility
import com.iflytek.cloud.SynthesizerListener
import com.iflytek.cloud.VoiceWakeuper
import com.iflytek.cloud.WakeuperListener
import com.iflytek.cloud.WakeuperResult
import com.iflytek.cloud.util.ResourceUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import org.jxxy.debug.common.util.getHeight
import org.jxxy.debug.common.util.getWidth
import org.jxxy.debug.common.util.loadAndPrepareByUrl
import org.jxxy.debug.corekit.util.dp
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.theme.AIAssistantSettings
import org.jxxy.debug.theme.AIAssistantSettings.CurrentEMOTendencies
import org.jxxy.debug.theme.AIAssistantSettings.emoCommands
import org.jxxy.debug.theme.AIAssistantSettings.planCommands
import org.jxxy.debug.theme.R
import org.jxxy.debug.theme.bean.ChatContent
import org.jxxy.debug.theme.bean.ChatConversation
import org.jxxy.debug.theme.bean.StreamData
import org.jxxy.debug.theme.databinding.VoiceWaterBinding
import org.jxxy.debug.theme.floatball.utils.Utils
import org.jxxy.debug.theme.http.repository.AiRepository
import org.jxxy.debug.theme.myListener.StreamResponseListener
import org.jxxy.debug.theme.posemon.PossMonitorActivity
import java.util.jar.Manifest

class VoiceService: LifecycleService() {
    private val TAG = "VoiceService"
    // 语音唤醒
    private val keep_alive = "1"
    private var ivwNetMode = "0"
    private var curThresh = 1450
    private var mIvw: VoiceWakeuper? = null
    // 语音合成
    private var mTts: SpeechSynthesizer? = null
    // 语音听写
    private var mIat: SpeechRecognizer? = null
    private var tatRes:StringBuffer?=null
    // 用于与其他的进行通讯
    private var isSayIng = false
    private var isListenIng = false
    private val repository by lazy { AiRepository() }
    private var willText = StringBuffer()
    private var sayQueue = ArrayDeque<String>()
    private val isListenIngState = MutableLiveData<Boolean>()
    private  var flag = false
    private lateinit var music:MediaPlayer
    private fun isRecordAudioPermissionGranted(): Boolean {
        return checkPermission(
            android.Manifest.permission.RECORD_AUDIO,
            Process.myPid(),
            Process.myUid()
        ) == PackageManager.PERMISSION_GRANTED
    }
    override fun onCreate() {
        super.onCreate()
        if (isRecordAudioPermissionGranted()){
            open()
        }else{
            "没有录音权限".toast()
        }
    }
    private fun open(){
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "="+getString(R.string.app_id))
        // 初始化对象
        mIvw = VoiceWakeuper.createWakeuper(this, null)
        mIvw = VoiceWakeuper.getWakeuper()
        if (mIvw != null) {
            // 清空参数
            mIvw!!.setParameter(SpeechConstant.PARAMS, null)
            // 唤醒门限值，根据资源携带的唤醒词个数按照“id:门限;id:门限”的格式传入
            mIvw!!.setParameter(SpeechConstant.IVW_THRESHOLD, "0:$curThresh")
            // 设置唤醒模式
            mIvw!!.setParameter(SpeechConstant.IVW_SST, "wakeup")
            // 设置持续进行唤醒
            mIvw!!.setParameter(SpeechConstant.KEEP_ALIVE, keep_alive)
            // 设置闭环优化网络模式
            mIvw!!.setParameter(SpeechConstant.IVW_NET_MODE, ivwNetMode)
            // 设置唤醒资源路径
            mIvw!!.setParameter(SpeechConstant.IVW_RES_PATH, weekResource)
            // 设置唤醒录音保存路径，保存最近一分钟的音频
            mIvw!!.setParameter(
                SpeechConstant.IVW_AUDIO_PATH,
                getExternalFilesDir("msc")!!.absolutePath + "/ivw.wav"
            )
            mIvw!!.setParameter(SpeechConstant.AUDIO_FORMAT, "wav")
            mIvw!!.startListening(
                mWakeuperListener
            )
        } else {
            "唤醒未初始化".toast()
        }
        mTts = SpeechSynthesizer.createSynthesizer(this, mTtsInitListener)
        mIat = SpeechRecognizer.createRecognizer(this, mTatInitListener)
        music =  MediaPlayer().loadAndPrepareByUrl(this.getString(org.jxxy.debug.theme.R.string.ai_music),this)
        subscribeUi()
    }
    private fun subscribeUi() {
        isListenIngState.observe(this){
            Log.d(TAG, "subscribeUi: beidiaoyong")
            if (it!=null&&it){
                listener()
            }
        }
    }

    private fun onWake(){
        if (isListenIngState.value== null  || !isListenIngState.value!!){
            "已唤醒".toast()
            say("我在")
        }
    }
    fun readySay(it:String){
        lifecycleScope.launch{
            willText.append(it)
            Log.d(TAG, "subscribeUi: zmc${willText.toString()}")
            if (it!=null){
                if (!isSayIng){
                    if(willText.length>10){
                        val toString = willText.toString()
                        willText = StringBuffer()
                        launch(Dispatchers.Main) {
                            say(toString)
                        }
                    }
                }
            }
        }
    }
    fun say(text:String){
        if (!isSayIng) {
            isSayIng = true
            setTtsParam()
            Log.d(TAG, "准备点击： " + System.currentTimeMillis())
            val code = mTts!!.startSpeaking(text, mTtsListener)
            if (code != ErrorCode.SUCCESS) {
                Log.d(TAG, "语音合成失败,错误码: $code,请点击网址https://www.xfyun.cn/document/error-code查询解决方案")
                "语音合成失败".toast()
            }
        }
    }
    fun listener(){
        Log.d(TAG, "listener: a")
        mIat?.let {
            tatRes = StringBuffer()
            setTatParam()
            val ret = mIat!!.startListening(mRecognizerListener)
            if (ret != ErrorCode.SUCCESS) {
                "听写失败".toast()
                Log.d(TAG,"听写失败,错误码：$ret,请点击网址https://www.xfyun.cn/document/error-code查询解决方案")
            } else {
            }
        }
    }
    private val chatConversation by lazy{ ChatConversation(ArrayList<ChatContent>()) }
    private fun chat(text: String) {
        Log.d("zmc", "chat开始: ${text}")
        if (text.length>1){
            if(AIAssistantSettings.CurrentEMOTendencies != AIAssistantSettings.Neutral){
                chatConversation.chatContentList?.add(AIAssistantSettings.EMOPromote)
                chatConversation.chatContentList?.add(ChatContent(ChatContent.assistant,"好的"))
            }
            chatConversation.chatContentList?.add(ChatContent(ChatContent.user,text))
            chatConversation.chatContentList = ArrayList(chatConversation.chatContentList?.takeLast(AIAssistantSettings.chatCnt))
            "分析中".toast()
            binding!!.waveView.startColor = org.jxxy.debug.corekit.util.ResourceUtil.getColor(org.jxxy.debug.common.R.color.red)
            setWave(25)
            setSpeed(60f)
           lifecycleScope.launch{
               requestChat(chatConversation.chatContentList as ArrayList<ChatContent>)
           }
//            lifecycleScope.launch(CoroutineExceptionHandler{ _, t-> Log.e("zmc", "chat: $t,${t.printStackTrace()}")}){
//                val chat = repository.chat(chatConversation)
////            val chat = repository.chatFast(chatConversation.chatContentList!!)
//                if (chat.code == 0){
//                    chat.data?.let {
//                        chatConversation.chatContentList?.add(ChatContent(ChatContent.assistant,it))
//                        Log.d(TAG, "chat开始: c1${chat.data}")
//                        say(it)
//                    }
//                }else{
//                    chatConversation.chatContentList?.let {
//                        if(it is ArrayList<ChatContent>){
//                            it.removeAt(it.size - 1)
//                        }
//                    }
//                    say("不好意思由于网络波动我刚刚打了个盹，你可以重新说一遍吗？")
//                }
//            }
        }
    }

    // 语音唤醒
    private val mWakeuperListener: WakeuperListener = object : WakeuperListener {
        override fun onResult(result: WakeuperResult) {
            try {
                val text = result.resultString
                val `object`: JSONObject
                `object` = JSONObject(text)
                if ("wakeup".equals(`object`.optString("sst"))){
                    Log.d(TAG, "onResult")
                    onWake()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        override fun onError(error: SpeechError) {
            error.getPlainDescription(true).toast()
        }
        override fun onBeginOfSpeech() {}
        override fun onEvent(eventType: Int, isLast: Int, arg2: Int, obj: Bundle?) {
            when (eventType) {
                SpeechEvent.EVENT_RECORD_DATA -> {
                    val audio = obj?.getByteArray(SpeechEvent.KEY_EVENT_RECORD_DATA)
                    Log.i(TAG, "ivw audio length: " + audio!!.size)
                }
            }
        }
        override fun onVolumeChanged(volume: Int) {}
    }
    private val weekResource: String
        private get() {
            val resPath = ResourceUtil.generateResourcePath(
                this,
                ResourceUtil.RESOURCE_TYPE.assets,
                "ivw/" + getString(R.string.app_id) + ".jet"
            )
            Log.d(TAG, "resPath: $resPath")
            return resPath
        }
    // 语音合成
    private val mTtsInitListener = InitListener { code ->
        Log.d(TAG, "InitListener init() code = $code")
        if (code != ErrorCode.SUCCESS) {
          "初始化失败,错误码：$code,请点击网址https://www.xfyun.cn/document/error-code查询解决方案".toast()
        } else {
            // 初始化成功，之后可以调用startSpeaking方法
            // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
            // 正确的做法是将onCreate中的startSpeaking调用移至这里
        }
    }
    private fun setTtsParam() {
        // 清空参数
        mTts!!.setParameter(SpeechConstant.PARAMS, null)
        //设置合成
        //设置使用云端引擎
        mTts!!.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD)
        //设置发音人
//            var voicerCloud = "xiaoyan"
        mTts!!.setParameter(SpeechConstant.VOICE_NAME, AIAssistantSettings.voicer)
        //mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY,"1");//支持实时音频流抛出，仅在synthesizeToUri条件下支持
        //设置合成语速
        mTts!!.setParameter(
            SpeechConstant.SPEED,
            AIAssistantSettings.voiceSpeed.toString()
        )
        //设置合成音调
        mTts!!.setParameter(
            SpeechConstant.PITCH,
            AIAssistantSettings.voicePitch.toString()
        )
        //设置合成音量
        mTts!!.setParameter(
            SpeechConstant.VOLUME,
            AIAssistantSettings.voiceVolume.toString()
        )
        //设置播放器音频流类型
        mTts!!.setParameter(
            SpeechConstant.STREAM_TYPE,"3")
        //	mTts.setParameter(SpeechConstant.STREAM_TYPE, AudioManager.STREAM_MUSIC+"");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts!!.setParameter(SpeechConstant.KEY_REQUEST_FOCUS,AIAssistantSettings.isBreakOnOtherVoice.toString())
    }
    fun handleCondition(delayMillis: Int) {
        isSayIng = false
        if (!flag) {
            lifecycleScope.launch {
                delay(delayMillis.toLong())
                Log.d(TAG, "subscribeUi: diaoyong")
                isListenIngState.postValue(true)
                setWave(0)
                setSpeed(0f)
            }
        }
    }
    private val mTtsListener: SynthesizerListener = object : SynthesizerListener {
        override fun onSpeakBegin() {
            //showTip("开始播放");
            Log.d(TAG, "开始播放：" + System.currentTimeMillis())
            if (binding!= null){
                binding!!.waveView.startColor = org.jxxy.debug.corekit.util.ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_300)
                setSpeed(15f)
                setWave(50)
            }else{
                showWater()
            }
        }
        override fun onSpeakPaused() {
            Log.d(TAG, "暂停播放：" + System.currentTimeMillis())
        }
        override fun onSpeakResumed() {
            Log.d(TAG, "继续播放：" + System.currentTimeMillis())
        }
        override fun onBufferProgress(
            percent: Int, beginPos: Int, endPos: Int,
            info: String
        ) {
        }
        override fun onSpeakProgress(percent: Int, beginPos: Int, endPos: Int) {
            // 播放进度
            Log.d(TAG, "onSpeakProgress: $percent $beginPos $endPos")
            if(isSayIng){
                if (endPos < 10 && percent >= 80) {
                    handleCondition(500)
                } else if (endPos < 30 && percent > 95) {
                    handleCondition(1000)
                } else if (percent == 99) {
                    handleCondition(500)
                }
            }
        }
        override fun onCompleted(error: SpeechError) {
            Log.d(TAG, "onCompleted: 播放完成")
            if (error == null) {
                Log.d(TAG, "播放完成：" + System.currentTimeMillis())
            } else {
                Log.d(TAG, "${error.getPlainDescription(true)}：" + System.currentTimeMillis())
            }
        }
        override fun onEvent(eventType: Int, arg1: Int, arg2: Int, obj: Bundle?) {
            if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                val sid = obj?.getString(SpeechEvent.KEY_EVENT_AUDIO_URL)
                Log.d(TAG, "session id =$sid")
            }
        }
    }
    // 语音听写
    private val mTatInitListener = InitListener { code ->
        Log.d(TAG, "SpeechRecognizer init() code = $code")
        if (code != ErrorCode.SUCCESS) {
            Log.d(TAG,   "初始化失败，错误码：$code,请点击网址https://www.xfyun.cn/document/error-code查询解决方案")
            "语音听写初始化失败".toast()
        }
    }
    fun setTatParam() {
        // 清空参数
        mIat?.setParameter(SpeechConstant.PARAMS, null)
        mIat?.setParameter( SpeechConstant.CLOUD_GRAMMAR, null );
        mIat?.setParameter( SpeechConstant.SUBJECT, null );
        val lag = AIAssistantSettings.TatLanguage
        // 设置语言
        mIat?.setParameter(SpeechConstant.LANGUAGE, lag)
        // 设置语言区域
        mIat?.setParameter(SpeechConstant.ACCENT, lag)
        // 设置引擎
        mIat?.setParameter(SpeechConstant.ENGINE_TYPE, AIAssistantSettings.tatEngineType)
        // 设置返回结果格式
        mIat?.setParameter(SpeechConstant.RESULT_TYPE, "json")
        mIat?.setParameter(
            SpeechConstant.VAD_BOS,
            AIAssistantSettings.tatVadbosPreference.toString()
        )
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat?.setParameter(
            SpeechConstant.VAD_EOS,
            AIAssistantSettings.tatVadeosPreference.toString()
        )

//
//        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
//        mIat!!.setParameter(SpeechConstant.AUDIO_FORMAT, "wav")
//        mIat!!.setParameter(
//            SpeechConstant.ASR_AUDIO_PATH,
//            getExternalFilesDir("msc")!!.absolutePath + "/iat.wav"
//        )
    }
    private val mRecognizerListener: RecognizerListener = object : RecognizerListener {
        var flag = false
        override fun onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            "请提问".toast()
            setWave(25)
            setSpeed(15f)
        }

        override fun onError(error: SpeechError) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
//            Log.d(TAG, "${error.getPlainDescription(true)}: ")
        }

        override fun onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            setSpeed(5f)
            setWave(25)
            flag = flag
            "录音结束".toast()
            Log.d(TAG, "结束说话: ")
        }

        override fun onResult(results: RecognizerResult, isLast: Boolean) {
            val text: String = JsonParser.parseIatResult(results.resultString)
            tatRes!!.append(text)
            if (isLast) {
                val toString = tatRes.toString()
                "你输入了"+toString.toast()
                Log.d(TAG, "zmc$toString: ")
                Log.d(TAG, "subscribeUi: beidiaoyongfalse")
                checkCmd(toString)
            }
        }
        override fun onVolumeChanged(volume: Int, data: ByteArray) {
           if (!flag){
               if (binding ==null){
                   binding!!.waveView.startColor = org.jxxy.debug.corekit.util.ResourceUtil.getColor(
                       org.jxxy.debug.common.R.color.red)
                   setSpeed(15f)
                   setWave(75)
               }
               flag = true
           }
        }
        override fun onEvent(eventType: Int, arg1: Int, arg2: Int, obj: Bundle?) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                val sid = obj?.getString(SpeechEvent.KEY_EVENT_AUDIO_URL)
                Log.d(TAG, "session id =$sid")
            }
        }
    }
    private fun checkCmd(toString: String) {
        Log.d(TAG, "checkCmd: aaa"+toString)
        if (toString.length<2){
            isListenIngState.postValue(false)
        }
        when(true){
            AIAssistantSettings.musicPlayCommands.any{toString.contains(it)} ->{
                music.start()
            }
            emoCommands.any{toString.contains(it)} ->{
                say(AIAssistantSettings.getEMO(CurrentEMOTendencies))
            }
            planCommands.any{toString.contains(it)} ->{
                say("今日的学习计划是背诵50个单词，学习最新ai资讯")
            }
            else ->{chat(toString)}
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mIat?.cancel()
        mIat?.destroy()
        mIvw?.cancel()
        mIvw?.destroy()
        mTts?.destroy()
    }
    var windowManager:WindowManager?=null
    var binding:VoiceWaterBinding?=null
    @SuppressLint("ClickableViewAccessibility")
    fun showWater(){
        if (windowManager == null) windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        binding = VoiceWaterBinding.inflate(LayoutInflater.from(this))
        val lp = WindowManager.LayoutParams().apply {
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            }
            format = PixelFormat.RGBA_8888
            flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            width = getWidth()
            height = 50.dp()
            gravity = Gravity.BOTTOM
        }
        binding?.waveView?.run {
            velocity = 15f
            colorAlpha = 0.75f
            gradientAngle = 90
            waveHeight = 50
            startColor =org.jxxy.debug.corekit.util.ResourceUtil.getColor(org.jxxy.debug.common.R.color.l_y)
            closeColor = org.jxxy.debug.corekit.util.ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_200)
            start()
        }
        windowManager!!.addView(binding!!.root,lp)
    }

    fun closeWater(){
        if (windowManager!=null&& binding!=null){
            windowManager!!.removeView(binding!!.root)
        }
    }
    var old = 15f
    fun setSpeed(speed: Float){
        if (windowManager!=null&& binding!=null){
            ObjectAnimator.ofFloat(
                binding!!.waveView,
                "velocity",
                old,
                speed
            ).apply {
                duration = 300
                start()
            }
            old = speed
        }
    }
    var oldh = 50
    fun setWave(h:Int){
        if (windowManager!=null&& binding!=null){
            ObjectAnimator.ofInt(
                binding!!.waveView,
                "waveHeight",
                oldh,
                h
            ).apply {
                duration = 300
                start()
            }
            if (h ==0 ){
                ObjectAnimator.ofInt(
                    binding!!.waveView,
                    "visibility",
                    View.VISIBLE,
                    View.INVISIBLE
                ).apply {
                    duration = 300
                    start()
                }
            }
            if (oldh ==0 && h!=0&&binding!!.waveView.visibility != View.GONE){
                ObjectAnimator.ofInt(
                    binding!!.waveView,
                    "visibility",
                    View.INVISIBLE,
                    View.VISIBLE
                ).apply {
                    duration = 300
                    start()
                }
            }
            oldh = h
        }
    }
     fun requestChat(list : ArrayList<ChatContent>){
         lifecycleScope.launch {
             repository.chat(ChatConversation(list)).data?.let {
                 val nowString : String = it
                 nowString.toString().toast()
                 say(nowString.toString())
             }
         }
    }
}