package org.jxxy.debug.theme.floatball.service

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.GradientDrawable
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.view.ViewCompat
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import org.jxxy.debug.common.bean.Node
import org.jxxy.debug.common.service.drawThinkMapNoAI
import org.jxxy.debug.common.service.goSearch
import org.jxxy.debug.common.service.goThinkMap
import org.jxxy.debug.common.util.close
import org.jxxy.debug.common.util.getHeight
import org.jxxy.debug.common.util.getWidth
import org.jxxy.debug.common.util.goneViews
import org.jxxy.debug.common.util.loadAndPrepareByUrl
import org.jxxy.debug.common.util.showViews
import org.jxxy.debug.common.util.start
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.dp
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.theme.AIAssistantSettings
import org.jxxy.debug.theme.R
import org.jxxy.debug.theme.activity.ReadPhotoActivity
import org.jxxy.debug.theme.adapter.ChildNodeAdapter
import org.jxxy.debug.theme.bean.ChatContent
import org.jxxy.debug.theme.bean.ChatConversation
import org.jxxy.debug.theme.bean.RespondBean
import org.jxxy.debug.theme.databinding.ServiceEqAnswerBinding
import org.jxxy.debug.theme.databinding.ServiceFloatItemBinding
import org.jxxy.debug.theme.databinding.ServiceNoteBinding
import org.jxxy.debug.theme.databinding.ServiceStudyBinding
import org.jxxy.debug.theme.databinding.ServiceTranslateDialogBinding
import org.jxxy.debug.theme.floatball.utils.ShadowNode
import org.jxxy.debug.theme.floatball.utils.ViewModleMain
import org.jxxy.debug.theme.http.repository.AiRepository
import org.jxxy.debug.theme.myListener.MyTouchListener
import org.jxxy.debug.theme.voice.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.max


// 整个项目写得最烂的代码，请直接划走。
class SuspendWindowService : LifecycleService() {
    private lateinit var windowManager: WindowManager
    private lateinit var inputMethodManager:InputMethodManager
    private val repository by lazy { AiRepository()}
    private lateinit var view: ServiceFloatItemBinding // 悬浮窗View
//    private val liveData by lazy { VoiceLiveData.instance }
    private val TAG = "zmc"
    private var isEQ = false
    override fun onCreate() {
        super.onCreate()
        if (isRecordAudioPermissionGranted()){
            open()
        }else{
            "没有录音权限".toast()
        }
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        inputMethodManager = getSystemService(androidx.lifecycle.LifecycleService.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        initObserve()
    }
    private fun initObserve() {
//        liveData.isWeak.observe(this){
//            if (it!=null&&it){
//                Log.d(TAG, "initObserve: 已监听到唤醒")
//                liveData.listenText.postValue(liveData.OPEN_LISTENER)
//            }
//        }
//        liveData.listenText.observe(this){
//            Log.d("zmc", "initObserve:听到 ${it}")
//            if (it!=null&&!liveData.OPEN_LISTENER.equals(it)){
//                Log.d("VoiceService", "initObserve: ${it}")
//                chat(it)
//            }
//        }
        ViewModleMain.isShowSuspendWindow.observe(this@SuspendWindowService) {
            if (it) {
//                BaseApplication.context().startService(Intent(this, VoiceService::class.java))
                showWindow()
            } else {
                if (windowManager!=null&&view!=null) {
                    windowManager.removeView(view.root)
//                    this.stopService(Intent(this, VoiceService::class.java))
                }
            }
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    private fun showWindow() {
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMetrics)
        var layoutParam = WindowManager.LayoutParams().apply {
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            }
            format = PixelFormat.RGBA_8888
            flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
            width = 145.dp()
            height = 145.dp()
//            width = WindowManager.LayoutParams.WRAP_CONTENT
//            height = WindowManager.LayoutParams.WRAP_CONTENT
//            gravity = Gravity.CENTER
            gravity = Gravity.TOP or Gravity.LEFT
            x = outMetrics.widthPixels - width
            y = getHeight()/2
        }
        // 新建悬浮窗控件
        view = ServiceFloatItemBinding.inflate(LayoutInflater.from(this))
        view.summaryBall.singleClick {
            showSummaryWindow()
            clickCenterBall()
        }
        updateShadowView(
            view.summaryBall,
            ShadowNode(
               45.dp<Int>().toInt(),
               45.dp<Int>().toInt(),
               10.dp<Int>().toInt(),
                0,
                0,
                Color.BLACK
            )
        )
        updateShadowView(
            view.centerBall,
            ShadowNode(
                45.dp<Int>().toInt(),
                45.dp<Int>().toInt(),
                8.dp<Int>().toInt(),
                0,
                0,
                Color.BLACK
            )
        )
        updateShadowView(
            view.noteBall,
            ShadowNode(
                45.dp<Int>().toInt(),
                45.dp<Int>().toInt(),
                8.dp<Int>().toInt(),
                0,
                0,
                Color.BLACK
            )
        )
        updateShadowView(
            view.EQBall,
            ShadowNode(
                45.dp<Int>().toInt(),
                45.dp<Int>().toInt(),
                8.dp<Int>().toInt(),
                0,
                0,
                Color.BLACK
            )
        )
        updateShadowView(
            view.translateBall,
            ShadowNode(
                45.dp<Int>().toInt(),
                45.dp<Int>().toInt(),
                4.dp<Int>().toInt(),
                0,
                0,
                Color.BLACK
            )
        )
        updateShadowView(
            view.studyBall,
            ShadowNode(
                45.dp<Int>().toInt(),
                45.dp<Int>().toInt(),
                4.dp<Int>().toInt(),
                0,
                0,
                Color.BLACK
            )
        )
        view.centerBall.background = ResourceUtil.getDrawable(org.jxxy.debug.common.R.drawable.round_white)

        view.noteBall.singleClick {
            showNoteWindow()
            clickCenterBall()
        }
        view.translateBall.singleClick {
            showTranslateWindow()
            clickCenterBall()
        }
        view.studyBall.singleClick {
            showStudy()
            clickCenterBall()
        }
        view.EQBall.singleClick {
            showEQWindow()
            clickCenterBall()
        }
        view.centerBall.tag = false
        view.centerBall.setOnTouchListener(
            MyTouchListener(
                view.root,windowManager,layoutParam,
                {
                    clickCenterBall()
                },
                {
                }
            )
        )
        windowManager.addView(view.root, layoutParam)
    }
    private var isTran = false
    private fun showTranslateWindow(){
        if (isTran) return
        isTran = true
        val view = ServiceTranslateDialogBinding.inflate(LayoutInflater.from(this))
        val layoutParams2 = WindowManager.LayoutParams().apply {
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            }
            format = PixelFormat.RGBA_8888
            flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            width = getWidth()/5*4
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.LEFT or Gravity.TOP
            x = getWidth() / 2 - width / 2
            y = getHeight()/5
        }
        view.run {
            pictureIcon.singleClick {
                val intent = Intent(applicationContext, ReadPhotoActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                applicationContext.startActivity(intent)
                windowManager.removeView(root)
                isTran = false
            }
            photoTv.singleClick {
                val intent = Intent(applicationContext, ReadPhotoActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                applicationContext.startActivity(intent)
                windowManager.removeView(root)
                isTran = false
            }
            root.setOnTouchListener(
                MyTouchListener(
                    root,
                    windowManager,
                    layoutParams2,
                    {},
                    {}
                )
            )
            backIcon.setOnTouchListener(
                MyTouchListener(
                    root,
                    windowManager,
                    layoutParams2,
                    { windowManager.removeView(root)
                        isTran = false},
                    {}
                )
            )
            translateTV.gone()
            lvc.gone()
            but.gone()
            aIV.load(ResourceUtil.getString(R.string.url_translate_top_right))
            view.root.setFocusable(true);
            view.root.setFocusableInTouchMode(true);
            view.translateET.setOnFocusChangeListener(editTextListener)
            view.submitButton.singleClick {
                val text = view.translateET.text.toString()
                if (text.length == 0) return@singleClick
                oldText  = text
                view.translateET.clearFocus()
                view.submitButton.gone()
                loadingAnimation.start()
                lifecycleScope.launch {
                        val async = async(Dispatchers.Default) {
                            repository.translate(text)
                        }
                        async.await().let {
                            it.enqueue(object : Callback<RespondBean> {
                                override fun onResponse(
                                    call: Call<RespondBean>,
                                    response: Response<RespondBean>
                                ) {
                                    val respondBean = response.body()
                                    respondBean?.let {
                                        view.translateTV.setText( it.trans_result?.get(0)?.dst ?: "error")
                                        stateIV.text = "快速翻译成功.点击分析原文本"
                                        loadingAnimation.close()
                                        translateTV.show()
                                        submitButton.show()
                                        but.show()
                                        ObjectAnimator.ofFloat(
                                            translateTV,
                                            "alpha",
                                            0f,
                                            1f
                                        ).apply {
                                            duration = 2000
                                            start()
                                        }
                                        ObjectAnimator.ofFloat(
                                            but,
                                            "alpha",
                                            0f,
                                            1f
                                        ).apply {
                                            duration = 2000
                                            start()
                                        }
                                    }
                                }
                                override fun onFailure(call: Call<RespondBean>, t: Throwable) {
                                }
                            })
                        }
                    }

            }
            but.singleClick {
                val text = oldText
                if (text.length == 0) return@singleClick
                but.gone()
                lvc.start()
                if ("entrepreneur".equals(text.toLowerCase())){
                    Log.d(TAG, "showTranslateWindow: aaaaaaaaa")
                    lifecycleScope.launch {
                        delay(3000)
                        launch(Dispatchers.Main) {
                            "分析成功".toast()
                            goThinkMap(applicationContext,42,Intent.FLAG_ACTIVITY_NEW_TASK)
                            windowManager.removeView(view.root)
                            isTran = false
                        }
                    }
                }else{
                    lifecycleScope.launch {
                    val node = async(Dispatchers.IO) {
                        repository.translate(text,text.contains(" "))
                    }
                    node.await().let {
                        it.data?.let {node ->
                            "分析成功".toast()
                            drawThinkMapNoAI(applicationContext,Intent.FLAG_ACTIVITY_NEW_TASK, node)
                            windowManager.removeView(view.root)
                            isTran =false
                        }
                    }
                }
                }
            }
        }
        windowManager.addView(view.root, layoutParams2)
    }
    var oldText = ""
    @SuppressLint("ClickableViewAccessibility")
    private fun showEQWindow() {
        if (isEQ){
            return
        }
        isEQ = true
        val binding = ServiceEqAnswerBinding.inflate(LayoutInflater.from(applicationContext))
        val layoutParams = WindowManager.LayoutParams().apply {
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            }
            format = PixelFormat.RGBA_8888
            flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            width = getWidth()/5*4
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.LEFT or Gravity.TOP
            x = getWidth()/2 - width/2
            y = getHeight()/2 - width/2
        }
        binding.run {
            answerTV.setOnTouchListener(
                object :View.OnTouchListener {
                    private var x = 0
                    private var y = 0
                    private var oledx = 0
                    private var oledy = 0
                    private val LONG_PRESS_TIME = 1000
                    private var lastTouchDownTime: Long = 0
                    private val maxHeight:Int
                        get() =  binding.answerTV.layout.height
                    private val height:Int
                        get() = binding.answerTV.height
                    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
                        when (motionEvent.action) {
                            MotionEvent.ACTION_DOWN -> {
                                lastTouchDownTime = System.currentTimeMillis()
                                x = motionEvent.rawX.toInt()
                                oledx = motionEvent.rawX.toInt()
                                y = motionEvent.rawY.toInt()
                                oledy = motionEvent.rawY.toInt()
                            }
                            MotionEvent.ACTION_MOVE -> {
                                val nowX = motionEvent.rawX.toInt()
                                val nowY = motionEvent.rawY.toInt()
                                val movedX = nowX - x
                                val movedY = nowY - y
                                x = nowX
                                y = nowY
                                if (view.canScrollVertically(-movedY)){
                                    view.scrollBy(0, -movedY)
                                }
                            }
                            else -> {
                            }
                        }
                        return true
                    }
                }
            )

            binding.logoIV.load(ResourceUtil.getString(R.string.url_logo),true)
            binding.root.setOnTouchListener(
                MyTouchListener(
                    binding.root,
                    windowManager,
                    layoutParams,
                    {},
                    {}
                )
            )
            backIcon.setOnTouchListener(
                MyTouchListener(
                    binding.root,
                    windowManager,
                    layoutParams,
                    { windowManager.removeView(binding.root)
                        isEQ = false},
                    {}
                )
            )

//            moveView.setOnTouchListener(MoveListener(root,windowManager,layoutParams))
            studyET.setOnFocusChangeListener(editTextListener)
            submitButton.text = "询问"
            submitButton.singleClick {
                val text = studyET.text.toString()
                if (text == ""){
                    "您未输入内容".toast()
                }else{
                    it.gone()
                    stateIV.text = ""
                    loadingAnimation.start()
                    hideKeyboard(studyET)
                    studyET.clearFocus()
                    Log.d("TAG", "showStudy: ${text}")
                    lifecycleScope.launch{
                        val node = repository.empatheticResponse(text)
                        studyET.setText("")
                        submitButton.show()
                        loadingAnimation.close()
                        node.let {
                            if (it.code?.toInt() == 0){
                                stateIV.text = "推荐回复"
                                binding.answerTV.text = it.data
                                ObjectAnimator.ofFloat(
                                    answerTV,
                                    "alpha",
                                    0f,
                                    1f
                                ).apply {
                                    duration = 2000
                                }.start()
                                binding.answerTV.show()
                                ObjectAnimator.ofFloat(
                                    copyIcon,
                                    "alpha",
                                    0f,
                                    1f
                                ).apply {
                                    duration = 2000
                                }.start()
                                copyIcon.show()
                                copyIcon.setOnTouchListener(
                                    MyTouchListener(
                                        binding.root,
                                        windowManager,
                                        layoutParams,
                                        {
                                            (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
                                                .setPrimaryClip(ClipData.newPlainText("","${it.data}"))
                                            "文字复制成功".toast()
                                        },
                                        {}
                                    )
                                )
                            }else{
                                stateIV.text = "分析失败"
                                "分析失败".toast()
                            }
                        }
                    }
                }
            }
        }
        windowManager.addView(binding.root,layoutParams)
    }
    private fun showNoteWindow(){
        drawThinkMapNoAI(applicationContext,Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    private var isStudy = false
    private fun showStudy(){
        if (isStudy) return
        isStudy = true
        val binding = ServiceStudyBinding.inflate(LayoutInflater.from(applicationContext))
        val layoutParams = WindowManager.LayoutParams().apply {
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            }
            format = PixelFormat.RGBA_8888
            flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            width = getWidth()/5*4
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.LEFT or Gravity.TOP
            x = getWidth()/2 - width/2
            y = getHeight()/2 - width/2
        }
        binding.run {
            binding.root.setOnTouchListener(
                MyTouchListener(
                    binding.root,
                    windowManager,
                    layoutParams,
                    {},
                    {}
                )
            )
            backIcon.setOnTouchListener(
                MyTouchListener(
                    binding.root,
                    windowManager,
                    layoutParams,
                    { windowManager.removeView(binding.root)
                        isStudy = false},
                    {}
                )
            )
            studyET.setOnFocusChangeListener(editTextListener)
            submitButton.singleClick {
                val text = studyET.text.toString()
                if (text == ""){
                    "您未输入内容".toast()
                }else if(text.equals("ai"))
                {
                    loadingAnimation.start()
                    hideKeyboard(studyET)
                    studyET.clearFocus()
                    submitButton.gone()
                    lifecycleScope.launch {
                        delay(5000)
                        launch {
                            submitButton.text = "前往"
                            submitButton.show()
                            loadingAnimation.close()
                            "分析完成\n点击按钮前往查看".toast()
                            submitButton.singleClick {
                                goThinkMap(applicationContext,2,Intent.FLAG_ACTIVITY_NEW_TASK)
                                windowManager.removeView(binding.root)
                                isStudy = false
                            }
                        }
                    }
                }
                else{
                    it.gone()
                    loadingAnimation.start()
                    hideKeyboard(studyET)
                    studyET.clearFocus()
                    Log.d("TAG", "showStudy: ${text}")
                    goSearch(applicationContext,Intent.FLAG_ACTIVITY_NEW_TASK,text)
                    lifecycleScope.launch{
                        val node =repository.study(text)
                        submitButton.text = "前往"
                        submitButton.show()
                        loadingAnimation.close()
                        "分析完成\n点击按钮前往查看".toast()
                        submitButton.singleClick {
                            Log.d("TAG", "showStudy: ${node}")
                            drawThinkMapNoAI(applicationContext,Intent.FLAG_ACTIVITY_NEW_TASK,node)
                            windowManager.removeView(binding.root)
                            isStudy = false
                        }
                    }
                }
            }
        }
        windowManager.addView(binding.root,layoutParams)
    }
    private var isSummary = false
    private fun showSummaryWindow(){
        if (isSummary) return
        isSummary = true
        val binding = ServiceNoteBinding.inflate(LayoutInflater.from(applicationContext))

        val layoutParams = WindowManager.LayoutParams().apply {
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            }
            format = PixelFormat.RGBA_8888
            flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            width = getWidth()/5*4
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.LEFT or Gravity.TOP
            x = getWidth() / 2 - width / 2
            y = getHeight()/5
        }
       binding.run {
           root.setOnTouchListener(
               MyTouchListener(
                   binding.root,
                   windowManager,
                   layoutParams,
                   {},
                   {}
               )
           )
           backIcon.setOnTouchListener(
               MyTouchListener(
                   binding.root,
                   windowManager,
                   layoutParams,
                   { windowManager.removeView(binding.root)
                       isSummary = false},
                   {}
               )
           )
           pictureIcon.singleClick {
               val intent = Intent(applicationContext, ReadPhotoActivity::class.java)
               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
               applicationContext.startActivity(intent)
               windowManager.removeView(binding.root)
               isSummary = false
           }
           photoTv.singleClick {
               val intent = Intent(applicationContext, ReadPhotoActivity::class.java)
               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
               applicationContext.startActivity(intent)
               windowManager.removeView(binding.root)
               isSummary = false
           }
           binding.root.setFocusable(true);
           binding.root.setFocusableInTouchMode(true);
           binding.rootET.setOnFocusChangeListener(editTextListener)
           binding.childNodeET.setOnFocusChangeListener(editTextListener)
           binding.run { goneViews(addButton,childNodeRV,childNodeTV,childNodeET) }
           binding.switchText.text = "添加分析方向"
           binding.addButton.text = "添加"
           binding.submitButton.text ="分析"
           aIV.load(
               ResourceUtil.getString(R.string.url_note_top_right)
           )
           val adapter: ChildNodeAdapter =ChildNodeAdapter()
           binding.childNodeRV.adapter = adapter
           binding.switchButton.setOnCheckedChangeListener { view2, isChecked ->
               if (isChecked){
                   binding.run {
                       showViews(addButton,childNodeRV,childNodeTV,childNodeET)
                   }
               }else{
                   binding.run { goneViews(addButton,childNodeRV,childNodeTV,childNodeET) }
               }
           }
           binding.addButton.singleClick {
               val string = binding.childNodeET.text.toString()
               if ("".equals(string)){
                   "子节点为空".toast()
               }else{
                   adapter.add(string)
                   binding.childNodeET.setText("")
                   inputMethodManager.hideSoftInputFromWindow(binding.childNodeET.windowToken, 0)
                   binding.childNodeET.clearFocus()
               }
           }
           binding.backIcon.singleClick {
               isSummary = false
               windowManager.removeView(binding.root)
           }
//        binding.moveView.setOnTouchListener(MoveListener(binding.root,windowManager,layoutParams))
           binding.submitButton.singleClick {
               val toString = binding.rootET.text.toString()
               if ("".equals(toString)){
                   "根节点为空".toast()
               }else {

                   val root = Node(adapter.list.map { Node(it) },toString)
                   if (true){
                        binding.submitButton.gone()
                       binding.loading.start()
                       lifecycleScope.launch{
                           if (adapter.list.size>0 && adapter.list[0].contains("AI是否抢走工作")){
                            delay(3000)
                            goThinkMap(applicationContext,43,Intent.FLAG_ACTIVITY_NEW_TASK)
                               isSummary = false
                               windowManager.removeView(binding.root)
                           }else{
                               val nodeNote = async { repository.nodeNote(root) }.await()
                               drawThinkMapNoAI(applicationContext,Intent.FLAG_ACTIVITY_NEW_TASK, nodeNote)
                               isSummary = false
                               windowManager.removeView(binding.root)
                           }
                       }
                   }else{
                       drawThinkMapNoAI(applicationContext,Intent.FLAG_ACTIVITY_NEW_TASK, root)
                       isSummary = false
                       windowManager.removeView(binding.root)
                   }
               }
           }
       }
        windowManager.addView(binding.root,layoutParams)
    }
    private fun hideKeyboard(view: View?) {
        view?.let {
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
    fun showKeyboard(view:View?){
        view?.let {
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
    val editTextListener = object :OnFocusChangeListener{
        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            if (hasFocus) {
                showKeyboard(v)
            } else {
                hideKeyboard(v)
            }
        }
    }
    private fun clickCenterBall(){
        if (this@SuspendWindowService.view.centerBall.tag as Boolean) {
            this@SuspendWindowService.view.root.transitionToStart()
//            this@SuspendWindowService.view.centerBall.text = "知"
            this@SuspendWindowService.view.centerBall.tag = false
        } else {
            this@SuspendWindowService.view.root.transitionToEnd()
//            this@SuspendWindowService.view.centerBall.text = "了"
            this@SuspendWindowService.view.centerBall.tag = true
        }
    }
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
    private var willText = StringBuffer()
    private var sayQueue = ArrayDeque<String>()
    private val isListenIngState = MutableLiveData<Boolean>()
    private  var flag = false
    private lateinit var music: MediaPlayer
    private fun isRecordAudioPermissionGranted(): Boolean {
        return checkPermission(
            android.Manifest.permission.RECORD_AUDIO,
            Process.myPid(),
            Process.myUid()
        ) == PackageManager.PERMISSION_GRANTED
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
            if (it!=null&&it){
                Log.d(TAG, "要听了")
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
            chatConversation.chatContentList = ArrayList(chatConversation.chatContentList?.takeLast(
                AIAssistantSettings.chatCnt))
            "录音结束，你输入了 ${text}\n 分析中".toast()
            binding!!.waveView.startColor = org.jxxy.debug.corekit.util.ResourceUtil.getColor(org.jxxy.debug.common.R.color.red)
            setWave(15)
            setSpeed(15f)
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
        else{
            setSpeed(0f)
            setWave(0)
            Log.d(TAG, "结束对话")
            "对话结束".toast()
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
            val resPath = com.iflytek.cloud.util.ResourceUtil.generateResourcePath(
                this,
                com.iflytek.cloud.util.ResourceUtil.RESOURCE_TYPE.assets,
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
        mTts!!.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, AIAssistantSettings.isBreakOnOtherVoice.toString())
    }
    fun handleCondition(delayMillis: Int) {
        isSayIng = false
        if (!flag) {
            lifecycleScope.launch {
                delay(delayMillis.toLong())
                Log.d(TAG, "say（）结束，大概")
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
                setSpeed(8f)
                setWave(20)
                binding!!.waveView.show()
            }else{
//                showWater()
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
//            Log.d(TAG, "onSpeakProgress: $percent $beginPos $endPos")
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
            AIAssistantSettings.tatVadbosPreference.toString()
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
            binding.waveView.startColor = ResourceUtil.getColor(org.jxxy.debug.common.R.color.l_g)
            setWave(15)
            setSpeed(8f)
        }

        override fun onError(error: SpeechError) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
//            Log.d(TAG, "${error.getPlainDescription(true)}: ")
        }

        override fun onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            setSpeed(3f)
            setWave(15)
            flag = flag
            Log.d(TAG, "结束listener(): ")
        }

        override fun onResult(results: RecognizerResult, isLast: Boolean) {
            val text: String = JsonParser.parseIatResult(results.resultString)
            tatRes!!.append(text)
            if (isLast) {
                val toString = tatRes.toString()
                checkCmd(toString)
            }
        }
        override fun onVolumeChanged(volume: Int, data: ByteArray) {
            if (!flag){
                if (binding ==null){
                    binding!!.waveView.startColor = org.jxxy.debug.corekit.util.ResourceUtil.getColor(
                        org.jxxy.debug.common.R.color.red)
                    setSpeed(8f)
                    setWave(25)
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
        Log.d(TAG, "听到"+toString)
        if (toString.length<2){
            isListenIngState.postValue(false)
        }
        when(true){
            AIAssistantSettings.musicPlayCommands.any{toString.contains(it)} ->{
                music.start()
            }
            AIAssistantSettings.emoCommands.any{toString.contains(it)} ->{
                say(AIAssistantSettings.getEMO(AIAssistantSettings.CurrentEMOTendencies))
            }
            AIAssistantSettings.planCommands.any{toString.contains(it)} ->{
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
    val binding:ServiceFloatItemBinding
        get() {return view}
    @SuppressLint("ClickableViewAccessibility")
//    fun showWater(){
//        if (windowManager == null) windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
//        binding = VoiceWaterBinding.inflate(LayoutInflater.from(this))
//        val lp = WindowManager.LayoutParams().apply {
//            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//            } else {
//                WindowManager.LayoutParams.TYPE_PHONE
//            }
//            format = PixelFormat.RGBA_8888
//            flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
//            width = getWidth()
//            height = 50.dp()
//            gravity = Gravity.BOTTOM
//        }
//        binding?.waveView?.run {
//            velocity = 15f
//            colorAlpha = 0.75f
//            gradientAngle = 90
//            waveHeight = 50
//            startColor =org.jxxy.debug.corekit.util.ResourceUtil.getColor(org.jxxy.debug.common.R.color.l_y)
//            closeColor = org.jxxy.debug.corekit.util.ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_200)
//            start()
//        }
//        windowManager!!.addView(binding!!.root,lp)
//    }

    fun closeWater(){
        if (windowManager!=null&& binding!=null){
            windowManager!!.removeView(binding!!.root)
        }
    }
    var old = 5f
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
    var oldh = 15
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
                binding.waveView.hide()
                binding.aIv.alpha = 1f
                binding.centerBall.background = ResourceUtil.getDrawable(org.jxxy.debug.common.R.drawable.round_white)
            }else{
                binding.waveView.show()
                binding.aIv.alpha = 0f
                binding.centerBall.background = ResourceUtil.getDrawable(R.drawable.p_200_2dp)
            }
            oldh = h
        }
    }
    suspend fun requestChat(list : ArrayList<ChatContent>){
        lifecycleScope.launch{
            repository.chat(ChatConversation(list)).data?.let {
                val nowString : String = it
                nowString.toString().toast()
                say(nowString.toString())
            }
        }
    }
    fun updateShadowView(view: View, shadowNode: ShadowNode) {
        val shadowColor = shadowNode.shadowColor
        val shadowRadius = shadowNode.shadowRadius
        val shadowOffsetX = shadowNode.shadowOffsetX
        val shadowOffsetY = shadowNode.shadowOffsetY
        val shadowDrawable = GradientDrawable()
        shadowDrawable.setColor(Color.WHITE)
        shadowDrawable.cornerRadius = 45f
        shadowDrawable.setStroke(2, Color.LTGRAY)
        shadowDrawable.setSize(shadowNode.width, shadowNode.height)
        view.background = shadowDrawable
        ViewCompat.setElevation(view, shadowRadius.toFloat())
        val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(shadowOffsetX, shadowOffsetY, shadowOffsetX, shadowOffsetY)
        view.layoutParams = layoutParams
        val shadowPaint = Paint()
        shadowPaint.color = shadowColor
        shadowPaint.style = Paint.Style.FILL
        shadowPaint.setShadowLayer(shadowRadius.toFloat(), shadowOffsetX.toFloat(), shadowOffsetY.toFloat(), shadowColor)
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, shadowPaint)
    }
}