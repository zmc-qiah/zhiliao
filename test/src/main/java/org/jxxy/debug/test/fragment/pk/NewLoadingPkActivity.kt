package org.jxxy.debug.test.fragment.pk

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jxxy.debug.common.http.BaseClientThread
import org.jxxy.debug.common.util.start
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.corekit.mmkv.PersistenceUtil
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.test.databinding.ActivityLoadingPkBinding
import org.jxxy.debug.test.fragment.bean.UserInfo
import org.jxxy.debug.test.fragment.viewModel.PkViewModel


class NewLoadingPkActivity : BaseActivity<ActivityLoadingPkBinding>() {
    private lateinit var userInfo: UserInfo
    private val TAG = "PKGAME"

    val viewModel : PkViewModel by lazy {
        ViewModelProvider(this).get(PkViewModel::class.java)
    }

    var loadingTime = 60
    lateinit var timer : CountDownTimer
    var flag = false
    var number = 0

    override fun bindLayout(): ActivityLoadingPkBinding {
        return ActivityLoadingPkBinding.inflate(layoutInflater)
    }

    override fun initView() {
        view.loadingLVJ.start()
        initTimer()
    }
    val name:String
        get(){
            return PersistenceUtil.getValue<String>("userName")?:"游客"
        }

    override fun subscribeUi() {
        viewModel.joinPkLiveData.observe(this){
            it.onSuccess {
                it?.let {
                    Log.d(TAG, "onClick: 开始请求")
                    "开始匹配".toast()
                     userInfo = it
                    view.logoIv.load(it.headPicture,true)
                    initThread()
                }
            }
        }
        viewModel.joinPk()
    }
    private fun initTimer() {
        timer = object : CountDownTimer((loadingTime * 1000).toLong(), 1000) {
            override fun onFinish() {}
            override fun onTick(millisUntilFinished: Long) {
                if (millisUntilFinished / 1000 == 0L) {
                    if (!flag) {
                        //如果匹配未成功则结束匹配等候
                        finish()
                        timer.cancel()
                    }
                }
                if (millisUntilFinished / 1000 == 1L) {
                    if (flag) {
                        view.loadingTv.setText("匹配成功")
                    } else {
                        view.loadingTv.setText("匹配失败")
                    }
                } else {
                    if(!flag) {
//                        viewModel.getJoin()
                    }
                    view.loadingTv.setText("已经匹配:" + (loadingTime - millisUntilFinished / 1000) + "秒")
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(timer != null){
            timer.cancel()
        }
    }
    private fun initThread() {
        mHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                Log.d(TAG, "handleMessage: ${msg.obj}")
                if (msg.what == 0) {
                    val content: String = msg.obj as String
                    val gson = Gson()
                    val dataType = object : TypeToken<BaseResp<List<UserInfo>>>() {}.type
                    val res:BaseResp<List<UserInfo>> = gson.fromJson(content, dataType)
                    if (res.code == 0) {
                        res.data?.let {
                            it.forEach {
                                if (it.userName!=userInfo.userName){
                                    view.aIv.load(it.headPicture,true)
                                }
                            }
                            view.loadingTv.setText("匹配成功")
                            "匹配成功".toast()
                            flag = true
                            connectionThread?.stopThread()
                            lifecycleScope.launch{
                                view.aIv.show()
                                val scaleXAnimator: ObjectAnimator =
                                    ObjectAnimator.ofFloat<View>(view.aIv, View.SCALE_X, 0f, 1f)
                                val scaleYAnimator: ObjectAnimator =
                                    ObjectAnimator.ofFloat<View>(view.aIv, View.SCALE_Y, 0f, 1f)
                                val alphaAnimator: ObjectAnimator =
                                    ObjectAnimator.ofFloat<View>(view.aIv, View.ALPHA, 0f, 1f)
                                scaleXAnimator.setDuration(1000); // 动画持续1秒
                                scaleYAnimator.setDuration(1000);
                                alphaAnimator.setDuration(1000);
                                val animatorSet = AnimatorSet()
                                animatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator)
                                animatorSet.start();
                                delay(2500)
                                launch(Dispatchers.Main) {
                                    this@NewLoadingPkActivity.startActivity(NewPkActivity::class.java)
                                    finish()
                                }
                            }
                        }
                    }
                }
            }
        }
        connectionThread = BaseClientThread(mHandler)
        connection = Thread(connectionThread)
        connection.start()
    }
    private var connectionThread: BaseClientThread? = null
    private lateinit var connection: Thread
    private lateinit var mHandler: Handler
}