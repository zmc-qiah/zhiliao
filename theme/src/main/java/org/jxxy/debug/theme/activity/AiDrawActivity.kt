package org.jxxy.debug.theme.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.luck.picture.lib.PictureSelectorPreviewFragment
import com.luck.picture.lib.basic.IBridgeViewLifecycle
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.style.PictureSelectorStyle
import com.luck.picture.lib.style.TitleBarStyle
import com.luck.picture.lib.widget.PreviewTitleBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jxxy.debug.common.util.BookMarkUtil
import org.jxxy.debug.common.util.GlideEngine
import org.jxxy.debug.common.util.close
import org.jxxy.debug.common.util.start
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.theme.R
import org.jxxy.debug.theme.adapter.AiDrawAdapter
import org.jxxy.debug.theme.adapter.GuidePageAdapter
import org.jxxy.debug.theme.databinding.ActivityAiDrawBinding
import org.jxxy.debug.theme.dialog.ChatGameDialog
import org.jxxy.debug.theme.fragment.GuideFragment
import org.jxxy.debug.theme.http.viewModel.MAiDrawViewModel


class AiDrawActivity : BaseActivity<ActivityAiDrawBinding>() {
    var string: String = ""
    val viewModel: MAiDrawViewModel by lazy {
        ViewModelProvider(this).get(MAiDrawViewModel::class.java)
    }
    lateinit var adapter : AiDrawAdapter
    lateinit var timer: CountDownTimer
    val title : String = "AI绘画"
    val rule : String = "使用方法:你可以在心中想象一个画面，然后尝试具体地将画面描述给ai，ai会将你所描述的图片生成出来，尽情发挥你的想象力！"
    lateinit var ruleDialog: ChatGameDialog
    private lateinit var drawList :ArrayList<LocalMedia>
    override fun bindLayout(): ActivityAiDrawBinding {
        return ActivityAiDrawBinding.inflate(layoutInflater)
    }
    val ui by lazy {
        val style = PictureSelectorStyle()
        style.apply {
            val titleBarStyle = TitleBarStyle()
            titleBarStyle.previewDeleteBackgroundResource = org.jxxy.debug.common.R.drawable.share1
            this.titleBarStyle = titleBarStyle
        }
    }


    override fun initView() {
//        adapter = AiDrawAdapter()
//        view.aiDrawRv.adapter = adapter
//        view.aiDrawRv.layoutManager = LinearLayoutManager(this)
//        view.aiDrawRv.addItemDecoration(CommonItemDecoration(10f))
//        adapter.add(AiDrawItem(1))
//        adapter.add(AiDrawItem(2))
        view.aiDrawBackgroundIv.load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/15/05880ce7-3bb9-4ba8-8ea3-5516b639b22d.jpg")
        drawList = ArrayList<LocalMedia>()
        view.aiDrawIv.singleClick {
            if (drawList.size == 0) return@singleClick
            PictureSelector.create(this)
                .openPreview()
                .setImageEngine(GlideEngine.createGlideEngine())
                .setSelectorUIStyle(ui)
                .startActivityPreview(drawList.size-1, true, drawList)
        }

        val adapter = GuidePageAdapter(this)
        adapter.list.add(GuideFragment("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/15/bb0bf860-5c13-486c-b0a9-0eee643951f7.png","第一页"))
        adapter.list.add(GuideFragment("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/15/80635339-1138-4cf9-84d0-63e57dad6f2b.jpg","第二页"))
        val fragment = GuideFragment("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/15/610836bf-96eb-438b-9470-a0f69a8065c2.jpg","第三页",1)
        fragment.way = {
            view.aiDrawVp.hide()
            view.aiDrawToolbar.post {
                val positions = IntArray(2)
                view.aiDrawToolbar.getLocationOnScreen(positions)
                BookMarkUtil.addView(this, positions[1] + view.aiDrawToolbar.height)
                BookMarkUtil.get(1,true)
            }
        }
        fragment.waySecond = {
            ruleDialog = ChatGameDialog(title,rule)
            ruleDialog.show(supportFragmentManager)
        }
        adapter.list.add(fragment)
        view.aiDrawVp.adapter = adapter


        view.aiDrawBtn.singleClick {
            val string = view.aiDrawEt.text.toString()
            viewModel.getPictureId(0, string)
            view.aiDrawEt.isEnabled = false
        }
//        view.testWaveView.setInnerRadius(100f)
    }

    override fun subscribeUi() {

        viewModel.getPictureIdLiveData.observe(this) {
            Log.d("我进入了getPicture", "我进入了getPicture")
            it.onSuccess {
                Log.d("getPicture数据", "${it}")
                Log.d("getPicture成功", "1")
                string = it!!
                initTimer(25)
//                string = it?.id!!
//                initTimer(it?.estimate?.toInt()!!)
                view.aiDrawProgressBar.show()
                view.aiDrawProgressBar.start()
            }
            it.onError { error, response ->
                Log.d("getPicture失败", "1")
                Log.d("error", "$error")
                Log.d("response", "$response")
            }
        }
//        viewModel.getPictureId("层层叠叠的玫瑰花开在山坡上,天空中都是白云")
        viewModel.getPictureByIdLiveData.observe(this) {
            Log.d("我进入了getPictureById", "我进入了getPictureById")
            Log.d("getbyid的数据", "${it.response}")
            it.onSuccess {
                Log.d("getbyid的数据", "${it}")
                Log.d("getbyid成功", "1")
                drawList.add(LocalMedia.generateHttpAsLocalMedia(it))
                view.aiDrawIv.load(it,5)
//                view.aiDrawIv.load(it?.gen_img)
                view.aiDrawEt.isEnabled = true
                view.aiDrawProgressBar.close()
                view.aiDrawProgressBar.hide()
            }
            it.onError { error, response ->
                Log.d("getbyid失败", "1")
                error?.printStackTrace()
                Log.d("response", "$response")
            }
        }
    }

    fun initTimer(time: Int) {
        timer = object : CountDownTimer((time * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                viewModel.getPictureById(string)
            }
        }.start()
    }

    override fun onDestroy() {
        BookMarkUtil.removeView()
        super.onDestroy()
    }
}