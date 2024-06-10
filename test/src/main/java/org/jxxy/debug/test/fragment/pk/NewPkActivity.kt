package org.jxxy.debug.test.fragment.pk

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jxxy.debug.common.http.BaseClientThread
import org.jxxy.debug.common.util.loadAndPrepareByUrl
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.corekit.mmkv.PersistenceUtil
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.test.R
import org.jxxy.debug.test.databinding.ActivityPkBinding
import org.jxxy.debug.test.fragment.adapter.PKAdapter
import org.jxxy.debug.test.fragment.bean.PkChoose
import org.jxxy.debug.test.fragment.bean.PkState
import org.jxxy.debug.test.fragment.bean.Question
import org.jxxy.debug.test.fragment.bean.UserInfo
import org.jxxy.debug.test.fragment.fragment.PKFragmentDialog
import org.jxxy.debug.test.fragment.myListener.ItemClickListener
import org.jxxy.debug.test.fragment.viewModel.PkViewModel

class NewPkActivity :BaseActivity<ActivityPkBinding>() {

    private val TAG = "PKGAME"
    val viewModel: PkViewModel by lazy {
        ViewModelProvider(this).get(PkViewModel::class.java)
    }
    val list: ArrayList<Question> = ArrayList()
    var index = 0
    var firstPlayerLastScore = 0
    var secondPlayerLastScore = 0
    var timer: CountDownTimer? = null
    var waitTimer: CountDownTimer? = null
    var useTime: Int = 10
    private lateinit var adapter: PKAdapter
    val waitTime: Int = 10
    var member: Int = -1
    private var userInfo: UserInfo ?=null
    private var firstClick:Boolean = true
    val name:String
    get(){
        return PersistenceUtil.getValue<String>("userName")?:"游客"
    }
    private lateinit var mHandler: Handler
    private lateinit var mClientThread: BaseClientThread

    override fun bindLayout(): ActivityPkBinding {
        return ActivityPkBinding.inflate(layoutInflater)
    }
    private lateinit var rightVideo:MediaPlayer
    private lateinit var errorVideo:MediaPlayer
    override fun initView() {
        errorVideo =MediaPlayer().loadAndPrepareByUrl(ResourceUtil.getString(R.string.right_video),this)
        rightVideo =MediaPlayer().loadAndPrepareByUrl(ResourceUtil.getString(R.string.error_video),this)
        loadThread()
        view.vsIV.load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/03/479cb349-73a3-49e8-942d-fdbb20983f89.png")
        view.pkRv.layoutManager = GridLayoutManager(this, 2)
        view.pkRv.addItemDecoration(SpanItemDecoration(5f, 5f, 2))
    }
    val onItemClickListener = object :ItemClickListener{
        override fun onItemClick(view: View, position: Int, entity: PkChoose) {
            if (firstClick){
                pauseTimer()
                var point = 0
                if (adapter.checkAnswer(position)){
                    point = elapsedTime.toInt()
                }
                val s = PkState(userInfo, point,list.size == index,position)
                val message = Message()
                message.what  = 0
                message.obj = s
                Log.d(TAG, "onItemClick: 主线程往Socket线程发送${message.obj}")
                mClientThread.revHandler.sendMessage(message)
            }
        }
    }
    private fun loadThread() {
        mHandler = object : Handler(Looper.getMainLooper()) {
            var flag = true
            override fun handleMessage(msg: Message) {
                val content: String = msg.obj as String
                Log.d(TAG, "主线程接收到Socket线程发送的${msg.obj}")
                val gson = Gson()
                val dataType = object : TypeToken<BaseResp<String>>() {}.type
                val res:BaseResp<String> = gson.fromJson(content, dataType)
                if (res.code == 0) {
                    val json = res.data.toString()
                    val pkState = gson.fromJson(json, PkState::class.java)
                    if (pkState!= null&&pkState.postion !=-1){
                        if (name.equals(pkState.userInfo?.userName)){
                            adapter.show(pkState.postion,0)
                            if (adapter.checkAnswer(pkState.postion)){
                                applyFadeInAnimation(view.firstPlayerScoreTv,view.firstPlayerAddTv,pkState.point)
                                rightVideo.start()
                            }else{
                                errorVideo.start()
                            }
                        }else{
                            adapter.show(pkState.postion,1)
                            applyFadeInAnimation(view.secondPlayerScoreTv,view.secondPlayerAddTv,pkState.point)
                        }
                        flag = !flag
                        if (flag){
                            if (pkState.isEnd){
                                var type  = 0
                                if (view.firstPlayerScoreTv.text.toString().toInt()>view.secondPlayerScoreTv.text.toString().toInt()){
                                    type = 1
                                }
                                val dialog = PKFragmentDialog(type)
                                dialog.show(supportFragmentManager)
                            }else{
                                lifecycleScope.launch{
                                    delay(1000)
                                    showQuestion(index++)
                                }
                            }
                        }
                    }
                }
            }
        }
        mClientThread = BaseClientThread(mHandler)
        Thread(mClientThread).start()
    }
    override fun subscribeUi() {
        viewModel.getPkQuestion.observe(this) {
            it.onSuccess {
                it?.let {
                    list.addAll(it.questions!!)
                    it.userInfos?.let {
                        it.forEach {
                            if (name.equals(it.userName)){
                                userInfo = it
                                view.firstPlayerTv.text = it.userName
                                view.firstPlayerIv.load(it.headPicture, true)
                            }else{
                                view.secondPlayerTv.text = it.userName
                                view.secondPlayerIv.load(it.headPicture, true)
                            }
                        }
                    }
                    showQuestion(index++)
                }
            }
        }
        viewModel.getPkQuestion()
    }
    fun showQuestion(index: Int) {
        adapter = PKAdapter(onItemClickListener)
        val question = list[index]
        view.pkQuestionTv.text = question.questionText
        val questionList = ArrayList<PkChoose>()
        questionList.add(PkChoose(0, question.optionA))
        questionList.add(PkChoose(1, question.optionB))
        question.optionC?.let {
            questionList.add(PkChoose(2, question.optionC))
        }
        question.optionD?.let {
            questionList.add(PkChoose(3, question.optionD))
        }
        adapter.clearAndAdd(questionList)
        //测试
        adapter.trueAnswer = "A"
        timer?.cancel()
        view.pkRv.adapter = adapter
        initTimer()
    }
    private var elapsedTime: Long = 10
    private fun initTimer() {
        firstClick = true
        timer = object : CountDownTimer((waitTime * 1000).toLong(), 1000) {
            override fun onFinish() {
                var point = 0
                val s = PkState(userInfo, point,list.size == index, -1)
                val message = Message()
                message.what  = 0
                message.obj = s
                Log.d(TAG, "onItemClick: 主线程往Socket线程发送${message.obj}")
                mClientThread.revHandler.sendMessage(message)
            }
            override fun onTick(millisUntilFinished: Long) {
                elapsedTime = millisUntilFinished/1000
                view.pkTimeTv.text = "还剩${millisUntilFinished / 1000}秒"
                view.pkProgressBar.progress = (millisUntilFinished / 1000).toInt()
                if (!adapter.isTime) {
                    useTime = 10 - (millisUntilFinished / 1000).toInt()
                    adapter.isTime = true
                }
            }
        }.start()
    }
    fun applyFadeInAnimation(textView: TextView, addTextView: TextView, point: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            textView.text = (textView.text.toString().toInt() + point).toString()
            addTextView.text = "+$point"
            val fadeInAnimator = ObjectAnimator.ofFloat(addTextView, "alpha", 0f, 1f)
            fadeInAnimator.duration = 1000
            val fadeOutAnimator = ObjectAnimator.ofFloat(addTextView, "alpha", 1f, 0f)
            fadeOutAnimator.duration = 1000
            val animatorSet = AnimatorSet()
            animatorSet.playSequentially(fadeInAnimator, fadeOutAnimator)
            animatorSet.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        waitTimer?.cancel()
        timer?.cancel()
    }
    private fun pauseTimer() {
        timer?.cancel()
        timer = null
        firstClick = false
    }

}