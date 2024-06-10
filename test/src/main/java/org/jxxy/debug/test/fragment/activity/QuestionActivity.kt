package org.jxxy.debug.test.fragment.activity

import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.util.*
import org.jxxy.debug.test.R
import org.jxxy.debug.test.databinding.ActivityQuestionBinding
import org.jxxy.debug.test.fragment.adapter.AnswerRvAdapter
import org.jxxy.debug.test.fragment.adapter.AnswerSheetRvAdapter
import org.jxxy.debug.test.fragment.bean.Question
import org.jxxy.debug.test.fragment.fragment.BottomFragment
import org.jxxy.debug.test.fragment.fragment.QuestionFragment
import org.jxxy.debug.test.fragment.viewModel.QuestionViewModel
import java.util.*


class QuestionActivity : BaseActivity<ActivityQuestionBinding>() {
    val questions: ArrayList<Question> by lazy {
        ArrayList()
    }
    val viewModel: QuestionViewModel by lazy {
        ViewModelProvider(this).get(QuestionViewModel::class.java)
    }
    val answerSheetList: ArrayList<Int> by lazy {
        ArrayList()
    }
    var sum: Int? = null
    var name: String? = ""
    var midResource: GifDrawable? = null
    var inResource: GifDrawable? = null

    init {

    }

    override fun bindLayout(): ActivityQuestionBinding {
        return ActivityQuestionBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val sum = intent.getIntExtra("sum", 0)
        val type = intent.getIntExtra("type", 0)
        val id = intent.getIntExtra("id", 0)
        if (type == AnswerRvAdapter.SPECIAL) {
            Glide.with(view.BossInIv)
                .asGif()
                .load("https://imgs.aixifan.com/o_1d1is8bkp1c19nbg8k10gnppfh.gif")
                .into(object : SimpleTarget<GifDrawable>() {
                    override fun onResourceReady(
                        resource: GifDrawable,
                        transition: Transition<in GifDrawable>?
                    ) {
                        resource.setLoopCount(1)
                        view.BossInIv.scaleType = ImageView.ScaleType.FIT_XY
                        view.BossInIv.setImageDrawable(resource)
                        inResource = resource
                        view.BossInIv.scaleType = ImageView.ScaleType.FIT_XY
                    }
                })
//            (view.BossIv.drawable as GifDrawable).start()
            Glide.with(view.BossMidIv)
                .asGif()
                .load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/06/19549175-920c-4f99-81a0-6b0db32b50d6.gif")
                .into(object : SimpleTarget<GifDrawable>() {
                    override fun onResourceReady(
                        resource: GifDrawable,
                        transition: Transition<in GifDrawable>?
                    ) {
                        resource.setLoopCount(1)
                        view.BossMidIv.scaleType = ImageView.ScaleType.FIT_XY
                        view.BossMidIv.setImageDrawable(resource)
                        midResource = resource
                        view.BossMidIv.scaleType = ImageView.ScaleType.FIT_XY
                    }
                })
            Glide.with(view.BossOutIv)
                .load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/01/ac56761f-69b4-4bae-8ffe-180c6637471a.png")
                .into(view.BossOutIv)
            viewModel.changeBossWay = {
                if (it == 0) {
                    view.BossOutIv.show()
                    view.BossMidIv.hide()
                    view.BossInIv.hide()
                    inResource?.stop()
                    midResource?.stop()
                } else if (it == 1) {
                    view.BossOutIv.hide()
                    view.BossMidIv.hide()
                    view.BossInIv.show()
                    inResource?.startFromFirstFrame()
                } else {
                    view.BossOutIv.hide()
                    view.BossMidIv.show()
                    view.BossInIv.hide()
                    midResource?.startFromFirstFrame()

                }
            }

        }

        name = intent.getStringExtra("name")
        viewModel.trueMedia = MediaPlayer.create(
            this,
            Uri.parse("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/12/26607078-d909-400e-939f-5b143f74461c.mp3")
        )
        viewModel.trueMedia.isLooping = false
        viewModel.errorMedia = MediaPlayer.create(
            this,
            Uri.parse("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/02/1f32047c-ad04-4706-913b-0035f4fbf627.mp3")
        )
        viewModel.errorMedia.isLooping = false
        if (name != null) {
            viewModel.specialType = name!!
        }
//        val bottomSheetBehavior = BottomSheetBehavior.from(view.answerSheetRv)
//        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//        view.answerSheetIcon.singleClick {
//
//            val bottomSheet = BottomFragment()
//            bottomSheet.show(supportFragmentManager, "")
//        }
        for (i in 0..((sum - 1) / 10)) {
            viewModel.isGetQuestion.add(false)
        }
        val bottomFragment =
            BottomFragment(viewModel.isGetQuestion, viewModel.itemState, ArrayList(), sum!!)
        bottomFragment.onClick = object : AnswerSheetRvAdapter.OnClickListener {
            override fun replace(index: Int) {
                val fragment =
                    QuestionFragment(questions, index, viewModel, viewModel.chooseMap[index])
                viewModel.index.value = index
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.questionFl, fragment)
                transaction.commit()
            }

            override fun dismiss() {
                bottomFragment.dismiss()
            }

            override fun getQuestion(start: Int) {
                viewModel.getSpecialInner(name ?: "", start, 10)
            }
        }
        view.collectIcon.singleClick {
            val questionNow = questions[viewModel.index.value!!]
            if (questionNow.collectState == 0) {
                viewModel.addCollection(questionNow.id)
                view.collectIcon.text = ResourceUtil.getString(R.string.collection_icon_work)
                questionNow.collectState = 1
            } else {
                view.collectIcon.text = ResourceUtil.getString(R.string.collection_icon_normal)
                viewModel.deleteCollection(questionNow.id)
                questionNow.collectState = 0
            }
        }

        val title = when (type) {
            AnswerRvAdapter.COLLECTION -> {
                "收藏"
            }
            AnswerRvAdapter.WRONG -> {
                "错题"
            }
            AnswerRvAdapter.SPECIAL -> {
                "专项答题"
            }
            AnswerRvAdapter.ANSWER -> {
                "每日答题"
            }
            else -> ""
        }
        view.questionToolbar.setTitleText(title)
        when (type) {
            AnswerRvAdapter.WRONG, AnswerRvAdapter.COLLECTION -> {
                view.buttonLt.hide()
                view.wrongSumIv.hide()
                view.buttonCl.hide()
            }
        }
        view.buttonLt.singleClick {
            bottomFragment.show(supportFragmentManager, "")
        }


        //判断题目类型的逻辑
//        if(id == 1) {
//            list.add(
//                QuestionTest(
//                    "1、( )是我们理解当前所处历史方位的关键词。　（　　）",
//                    listOf("新思想", "新举措", "新格局", "新时代"),
//                    listOf(0),
//                    "暂无解析"
//                )
//            )
//            list.add(
//                QuestionTest(
//                    ("2、( )和法治素养，是新时代大学生必须具备的基本素质。　（　　）"),
//                    listOf("思想政治素质", "思想道德素质", "道德素质", "个人修养"),
//                    listOf(1),
//                    "暂无解析"
//                )
//            )
//            questions = QuestionsTest(QuestionRvAdapter.SINGLE_SELECTION, list)
//        }else if(id == 2){
//            list.add(
//                QuestionTest(
//                    "1、( )是我们理解当前所处历史方位的关键词。　（　　）",
//                    listOf("新思想", "新举措", "新格局", "新时代"),
//                    listOf(0,3),
//                    "暂无解析"
//                )
//            )
//            list.add(
//                QuestionTest(
//                    ("2、( )和法治素养，是新时代大学生必须具备的基本素质。　（　　）"),
//                    listOf("思想政治素质", "思想道德素质", "道德素质", "个人修养"),
//                    listOf(1,2,3),
//                    "暂无解析"
//                )
//            )
//            questions = QuestionsTest(QuestionRvAdapter.MULTIPLE_SELECTION, list)
//        }else if(id == 3){
//            list.add(
//                QuestionTest(
//                    "1、( )是我们理解当前所处历史方位的关键词。　（　　）",
//                    listOf("正确", "错误"),
//                    listOf(0),
//                    "暂无解析"
//                )
//            )
//            list.add(
//                QuestionTest(
//                    ("2、( )和法治素养，是新时代大学生必须具备的基本素质。　（　　）"),
//                    listOf("正确", "错误"),
//                    listOf(1),
//                    "暂无解析"
//                )
//            )
//            questions = QuestionsTest(QuestionRvAdapter.JUDGMENT_QUESTION, list)
//        }
//

//        view.takeTv.singleClick {
//            if(viewModel.type.value != QuestionRvAdapter.TAKE) {
//                viewModel.type.value = QuestionRvAdapter.TAKE
//            }
//        }
//        view.reciteTv.singleClick {
//            if(viewModel.type.value != QuestionRvAdapter.RECITE)
//            viewModel.type.value = QuestionRvAdapter.RECITE
//        }

//        val bottomSheetLayout = view
//        Log.d("testtest","$bottomSheetLayout")
//        bottomSheetLayout.answerSheetRv.adapter = AnswerSheetRvAdapter(answerSheetList)
//        bottomSheetLayout.answerSheetRv.layoutManager = GridLayoutManager(this, 5)
//
//        val bottomSheetBehavior = BottomSheetBehavior.from(view.answerSheetRv.rootView)
//
//        view.answerSheetIcon.singleClick{
//            bottomSheetBehavior.run {
//                if (state == BottomSheetBehavior.STATE_EXPANDED) {
//                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//                }else {
//                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//                }
//            }
//        }
    }

    override fun subscribeUi() {

        sum = intent.getIntExtra("sum", 0)
        val type = intent.getIntExtra("type", 0)
        val id = intent.getIntExtra("id", 0)

        viewModel.rightCount.observe(this) {
            view.rightSumTv.text = it.toString()
        }
        viewModel.wrongCount.observe(this) {
            view.wrongSumTv.text = it.toString()
        }
        viewModel.questionLiveData.observe(this) {
            val url: String =
                "https://ts1.cn.mm.bing.net/th/id/R-C.ae9164183ecdb09c8e01ada7af40f15a?rik=K%2bv8Rw%2fp3%2foOWQ&riu=http%3a%2f%2fwww.kuaipng.com%2fUploads%2fpic%2fw%2f2019%2f08-24%2f69216%2fwater_69216_698_515.91_.png&ehk=vsV2PwFoQo01Wr1fT92jbnlkn1Ln4tpa2ZbWWwqV1wA%3d&risl=&pid=ImgRaw&r=0"

            it.onSuccess {
                it?.let {
                    questions.addAll(it.questions)
                    replaceFragment(
                        QuestionFragment(
                            questions,
                            viewModel.index.value!!,
                            viewModel,
                            viewModel.chooseMap[viewModel.index.value]
                        )
                    )
                    Log.d("TTTTTTTTTTTTTTT", "${questions.size}")
                }
            }
            it.onError { error, response ->
                Log.d("TTTTTTTTTTTTTTT", "我出错了")
            }
        }
        viewModel.collectionState.observe(this) {
            if (it == 0) {
                view.collectIcon.text = ResourceUtil.getString(R.string.collection_icon_normal)
            } else {
                view.collectIcon.text = ResourceUtil.getString(R.string.collection_icon_work)
            }
        }
        viewModel.getQuestionByIdLiveData.observe(this) {
            it.onSuccess {
                it?.let {
                    questions.add(it)
                    replaceFragment(
                        QuestionFragment(
                            questions,
                            0,
                            viewModel,
                            viewModel.chooseMap[0]
                        )
                    )
                }
                Log.d("Testsize", "${questions.size}")
            }

        }
        viewModel.getSpecialInnerLiveData.observe(this) {
            it.onSuccess {
                it?.let {
                    questions.addAll(it.questionInfos)
                    replaceFragment(
                        QuestionFragment(
                            questions,
                            0,
                            viewModel,
                            viewModel.chooseMap[0]
                        )
                    )
                }
            }
        }
        Log.d("TestType", "$type")
        Log.d("TestId", "$id")
        Log.d("TestSize", "${questions.size}")
        viewModel.answerType = type

        when (type) {
            AnswerRvAdapter.COLLECTION, AnswerRvAdapter.WRONG -> {
//                viewModel.getQuestions(10)
//                viewModel.isGetQuestion[0] = true
                viewModel.getQuestionById(id)
            }
            AnswerRvAdapter.SPECIAL -> {
                Log.d("TTTTTTTTTTTTT", "$name")
                viewModel.getSpecialInner(name!!, 1, 10)
                viewModel.isGetQuestion[0] = true
            }
            else -> {
                viewModel.getQuestions(5)
            }
        }
        if (sum != null) {
            view.pageTv.text = "1/${sum}"
        } else {
            view.pageTv.text = "1/5"
        }
        viewModel.startTime = System.currentTimeMillis()
        viewModel.index.observe(this) {
            if (sum != null) {
                view.pageTv.text = "${it + 1}/${sum}"
                viewModel.sum = sum!!
            } else {
                view.pageTv.text = "${it + 1}/5"
                viewModel.sum = 5
            }
        }
        viewModel.way = {
            finish()
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.questionFl, fragment)
        transaction.commit()
    }


}
