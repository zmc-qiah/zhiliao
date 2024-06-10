package org.jxxy.debug.test.fragment.activity

import android.content.pm.ActivityInfo
import android.widget.FrameLayout
import android.widget.MediaController
import androidx.lifecycle.ViewModelProvider
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.test.databinding.ActivityInterVideoBinding
import org.jxxy.debug.test.fragment.adapter.InterVideoChooseAdapter
import org.jxxy.debug.test.fragment.bean.InterVideoChoose
import org.jxxy.debug.test.fragment.fragment.MyBottomDialogFragment
import org.jxxy.debug.test.fragment.viewModel.QuestionVideoViewModel


class InterVideoActivity : BaseActivity<ActivityInterVideoBinding>() {
    val viewModel: QuestionVideoViewModel by lazy {
        ViewModelProvider(this).get(QuestionVideoViewModel::class.java)
    }

    override fun bindLayout(): ActivityInterVideoBinding {
        return ActivityInterVideoBinding.inflate(layoutInflater)
    }

    companion object {
        const val WIN = 0
        const val LOSE = 1
        const val NORMAL = 2
        const val RETURN = 4
        const val FINISH = 5
    }

    var id = 0
    var type = 2
    lateinit var list: ArrayList<InterVideoChoose>
    lateinit var fragment: MyBottomDialogFragment
    override fun initView() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        id = intent?.getIntExtra("id", 0) ?: 0
        type = intent?.getIntExtra("type", 2) ?: 2
        if(type == FINISH){
            finish()
        }
        val media = MediaController(this)

        val test = FrameLayout.LayoutParams(
            resources.displayMetrics.heightPixels,
            resources.displayMetrics.widthPixels
        )
        view.interVideoVv.layoutParams =
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )

//        view.interVideoVv.rotation = 90f
        list = ArrayList<InterVideoChoose>()
        fragment = MyBottomDialogFragment(list)
        fragment.listener = object : InterVideoChooseAdapter.OnItemClickListener {
            override fun onItemClick() {
                finish()
            }
        }
//        view.interVideoVv.setOnCompletionListener {
//            fragment.show(this.supportFragmentManager, "")
//        }
//        view.interVideoVv.setMediaController(media)
        view.interVideoVv.setVideoAllCallBack(object : VideoAllCallBack{
            override fun onStartPrepared(url: String?, vararg objects: Any?) {

            }

            override fun onPrepared(url: String?, vararg objects: Any?) {

            }

            override fun onClickStartIcon(url: String?, vararg objects: Any?) {

            }

            override fun onClickStartError(url: String?, vararg objects: Any?) {

            }

            override fun onClickStop(url: String?, vararg objects: Any?) {

            }

            override fun onClickStopFullscreen(url: String?, vararg objects: Any?) {

            }

            override fun onClickResume(url: String?, vararg objects: Any?) {

            }

            override fun onClickResumeFullscreen(url: String?, vararg objects: Any?) {

            }

            override fun onClickSeekbar(url: String?, vararg objects: Any?) {

            }

            override fun onClickSeekbarFullscreen(url: String?, vararg objects: Any?) {

            }

            override fun onAutoComplete(url: String?, vararg objects: Any?) {
                fragment.show(supportFragmentManager, "")
            }

            override fun onComplete(url: String?, vararg objects: Any?) {
            }

            override fun onEnterFullscreen(url: String?, vararg objects: Any?) {

            }

            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {

            }

            override fun onQuitSmallWidget(url: String?, vararg objects: Any?) {

            }

            override fun onEnterSmallWidget(url: String?, vararg objects: Any?) {

            }

            override fun onTouchScreenSeekVolume(url: String?, vararg objects: Any?) {

            }

            override fun onTouchScreenSeekPosition(url: String?, vararg objects: Any?) {

            }

            override fun onTouchScreenSeekLight(url: String?, vararg objects: Any?) {

            }

            override fun onPlayError(url: String?, vararg objects: Any?) {

            }

            override fun onClickStartThumb(url: String?, vararg objects: Any?) {

            }

            override fun onClickBlank(url: String?, vararg objects: Any?) {

            }

            override fun onClickBlankFullscreen(url: String?, vararg objects: Any?) {

            }
        })
        view.interVideoVv.setGSYVideoProgressListener{ progress, secProgress, currentPosition, duration ->
            // 在这里判断播放时间是否达到特定时间，然后执行操作
            if (progress >= 99) {
                view.interVideoVv.currentPlayer.onVideoPause()
                fragment.show(supportFragmentManager, "")
            }
        }
        view.interVideoVv.backButton.singleClick{
            finish()
            view.interVideoVv.onVideoPause()
        }
    }

    override fun subscribeUi() {
        viewModel.questionVideoByIdLiveData.observe(this) {
            it.onSuccess {
                it?.let { it1 ->
                    when (type) {
                        NORMAL -> {
                            view.interVideoVv.setUp(it1.videoUrl,true,"")
                        }
                        LOSE ->{
                            view.interVideoVv.setUp(it.videoResultList[0].videoUrl,true,"")
                        }
                        WIN ->{
                            view.interVideoVv.setUp(it.videoResultList[1].videoUrl,true,"")
                        }
                    }
                    view.interVideoVv.startPlayLogic()
                    when(type) {
                        NORMAL -> {
                            list.run {
                                add(InterVideoChoose(LOSE, it1.optionA,id))
                                add(InterVideoChoose(LOSE, it1.optionB,id))
                                if (!it.optionC.equals("")) {
                                    add(InterVideoChoose(LOSE, it.optionC,id))
                                }
                                if (!it.optionD.equals("")) {
                                    add(InterVideoChoose(LOSE, it.optionD,id))
                                }
                                val index = it1.answer.toCharArray()[0].toInt() - 'A'.toInt()
                                list[index] = InterVideoChoose(
                                    WIN, when (index) {
                                        0 -> it.optionA
                                        1 -> it.optionB
                                        2 -> it.optionC
                                        3 -> it.optionD
                                        else -> ""
                                    },
                                    id
                                )
                            }
                        }
                        WIN -> {
                            list.run {
                                add(InterVideoChoose(FINISH,"你赢了",id))
                            }
                        }
                        LOSE ->{
                            list.run {
                                add(InterVideoChoose(NORMAL,"返回",id))
                            }
                        }
                    }
                }
            }
        }
//        when (type) {
//            WIN -> {
//                view.interVideoVv.setVideoURI(Uri.parse(viewModel.winUrl))
//                view.interVideoVv.start()
//            }
//            LOSE -> {
//                view.interVideoVv.setVideoURI(Uri.parse(viewModel.loseUrl))
//                view.interVideoVv.start()
//            }
//            NORMAL -> {
//                viewModel.getQuestionVideoById(id)
//            }
//        }
        viewModel.getQuestionVideoById(id)
    }

    override fun onBackPressed() {
        view.interVideoVv.onVideoPause()
        super.onBackPressed()
    }

    override fun onDestroy() {
        view.interVideoVv.onVideoPause()
        super.onDestroy()
    }


}