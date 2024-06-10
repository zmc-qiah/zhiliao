package org.jxxy.debug.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.jxxy.debug.common.service.isLogin
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.gson.GsonManager
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.databinding.ActivityStartBinding
import org.jxxy.debug.login.activity.LoginActivity
import java.io.IOException
import java.net.URL
import java.util.*

class StartActivity : BaseActivity<ActivityStartBinding>() {

    lateinit var timer: CountDownTimer

    var idList: ArrayList<Int> = arrayListOf(26)

    override fun bindLayout(): ActivityStartBinding {
        return ActivityStartBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        window.setBackgroundDrawable(null)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun initView() {

        // 这个是通过计时器倒数3秒进入主界面的
        view.ivSkip.typeface = Typeface.createFromAsset(this.assets,"fonteditor.ttf");
        timer = object : CountDownTimer(1 * 1400, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            // 定时器执行完成的回调
            override fun onFinish() {
                a(false)
            }
        }
        timer.start()
        // 设置控件事件——通过点击跳过文本，结束计时进入主界面
        view.ivSkip.singleClick {
            a()
        }
    }
    fun a(flag:Boolean = true){
        if (flag){
            timer.cancel() // 停止计时器
        }
        if (!isLogin())        b.launch(Intent(this,LoginActivity::class.java))
        else {
            val context = this
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
    val b = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            startActivity<MainActivity>()
        }else{
            a(false)
        }
    }
    override fun subscribeUi() {
    }
}