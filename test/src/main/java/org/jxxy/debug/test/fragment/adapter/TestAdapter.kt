package org.jxxy.debug.test.fragment.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import cn.lentme.projects.whitetile.main.ThirdGameActivity
import com.bumptech.glide.Glide
import com.mdapp.Game2048.FirstGameActivity
import developer.abdusamid.puzzlesapp.activity.PuzzlesActivity
import org.jxxy.debug.common.service.AiService
import org.jxxy.debug.common.service.loginCheck
import org.jxxy.debug.corekit.common.CommonServiceManager
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.test.databinding.*
import org.jxxy.debug.test.fragment.activity.*
import org.jxxy.debug.test.fragment.bean.TestPart
import org.jxxy.debug.test.fragment.pk.NewLoadingPkActivity

class TestAdapter(val list: ArrayList<TestPart>) : MultipleTypeAdapter() {
    val url: String =
        "https://ts1.cn.mm.bing.net/th/id/R-C.ae9164183ecdb09c8e01ada7af40f15a?rik=K%2bv8Rw%2fp3%2foOWQ&riu=http%3a%2f%2fwww.kuaipng.com%2fUploads%2fpic%2fw%2f2019%2f08-24%2f69216%2fwater_69216_698_515.91_.png&ehk=vsV2PwFoQo01Wr1fT92jbnlkn1Ln4tpa2ZbWWwqV1wA%3d&risl=&pid=ImgRaw&r=0"
    lateinit var fragmentManager: FragmentManager

    companion object {
        const val TEST = 1
        const val TEXT = 2
        const val WRONG_AND_COLLECTION = 3
        const val GAME = 4
    }

    init {
        list.add(TestPart(TEXT, content = "答题"))
        list.add(TestPart(TEST))
        list.add(TestPart(TEXT, content = "功能"))
        list.add(TestPart(WRONG_AND_COLLECTION))
        list.add(TestPart(TEXT, content = "娱乐"))
        list.add(TestPart(GAME, 1))
        list.add(TestPart(GAME, 2))
        list.add(TestPart(GAME, 3))
        list.add(TestPart(GAME, 4))
        list.add(TestPart(GAME, 5))
//        list.add(TestPart(GAME,5))
        add(list)
    }

    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = when (viewType) {
        TEST -> TestViewHolder(ItemTestBinding.inflate(inflater, parent, false))
        TEXT -> TextViewHolder(ItemTextBinding.inflate(inflater, parent, false))
        WRONG_AND_COLLECTION -> WrongAndCollectionViewHolder(
            ItemWrongAndCollectionBinding.inflate(
                inflater,
                parent,
                false
            )
        )
        GAME -> GameViewHolder(ItemGameBinding.inflate(inflater, parent, false))
        else -> null
    }

    inner class TextViewHolder(val view: ItemTextBinding) :
        MultipleViewHolder<TestPart>(view.root) {
        override fun setHolder(entity: TestPart) {
            view.testTv.text = "${entity.content}"
        }
    }


    inner class TestViewHolder(val view: ItemTestBinding) :
        MultipleViewHolder<TestPart>(view.root) {
        override fun setHolder(entity: TestPart) {
            Glide.with(view.answerIv)
                .load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/01/c78d93a1-455a-4e2b-9208-4b0cba9e47fc.png")
                .into(view.answerIv)
//            view.answerIv.load("http://mms1.baidu.com/it/u=888195787,1173456300&fm=253&app=138&f=PNG?w=664&h=500")
            view.answerIv.scaleType = ImageView.ScaleType.FIT_XY
            view.answerRl.singleClick {
                loginCheck(context) {
                    val intent = Intent(context, QuestionActivity::class.java)
                    intent.putExtra("sum", 5)
                    intent.putExtra("type", AnswerRvAdapter.ANSWER)
                    context.startActivity(intent)
                }
            }
            Glide.with(view.testIv)
                .load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/01/e839e7a5-b493-4ae9-b69d-c328f3bd69d5.jpg")
                .into(view.testIv)
//            view.testIv.load("http://mms0.baidu.com/it/u=1619737172,3001322865&fm=253&app=138&f=JPEG?w=500&h=500")
            view.testIv.scaleType = ImageView.ScaleType.FIT_XY
            view.testRl.singleClick {
                loginCheck(context) {
                    val intent = Intent(context, AnswerListActivity::class.java)
                    intent.putExtra("type", AnswerRvAdapter.SPECIAL)
                    context.startActivity(intent)
                }
            }
            view.interVideoGameIv.load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/01/22c55db9-b888-436f-80ba-9302aa3f4e0d.jpg")
            view.interVideoGameIv.singleClick {
                context?.startActivity<InterVideoListActivity>()
            }
            view.pkRl.singleClick {
                loginCheck(context) {
                    context?.startActivity(NewLoadingPkActivity::class.java)
                }
            }
            Glide.with(view.pkIv)
                .load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/01/699e65aa-5dac-4581-bf31-2ed6b7f7de01.png")
                .into(view.pkIv)
            view.pkIv.scaleType = ImageView.ScaleType.FIT_XY
//            view.pkIv.load("http://mms1.baidu.com/it/u=821404267,3659966372&fm=253&app=138&f=PNG?w=499&h=356")
        }
    }

    inner class WrongAndCollectionViewHolder(val view: ItemWrongAndCollectionBinding) :
        MultipleViewHolder<TestPart>(view.root) {
        override fun setHolder(entity: TestPart) {
            view.run {
                view.statisticsRl.singleClick {
                    loginCheck(context) {
                        context?.startActivity<StatisticActivity>()
                    }
                }
                Glide.with(statisticsIv)
                    .load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/01/b8440529-4d79-4389-afce-cac4b49a68db.png")
                    .into(statisticsIv)
//                statisticsIv.load("http://mms2.baidu.com/it/u=806892091,3596060126&fm=253&app=120&f=PNG?w=500&h=500")
                statisticsIv.scaleType = ImageView.ScaleType.FIT_XY
                view.collectRl.singleClick {
                    loginCheck(context) {
                        context?.startActivity<CollectionActivity>()
                    }
                }
                Glide.with(collectIv)
                    .load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/01/57890514-e132-4cc6-8371-10797fdbed42.png")
                    .into(collectIv)
//                collectIv.load("http://mms1.baidu.com/it/u=653399465,2912431229&fm=253&app=120&f=PNG?w=573&h=500")
                collectIv.scaleType = ImageView.ScaleType.FIT_XY
                view.wrongRl.singleClick {
                    loginCheck(context) {
                        context?.startActivity<WrongActivity>()
                    }
                }
                Glide.with(wrongIv)
                    .load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/01/b4663db6-4f99-40fd-9fba-19abd2c1451b.jpg")
                    .into(wrongIv)
//                wrongIv.load("http://mms2.baidu.com/it/u=3400446790,735290036&fm=253&app=138&f=JPEG?w=500&h=500")
                wrongIv.scaleType = ImageView.ScaleType.FIT_XY
                view.achievementIv.load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/01/e70f3b8a-7c91-47a6-8a2d-d97f818b5398.png")
//                view.robotIv.singleClick {
//                    StatisticFragment().show(fragmentManager)
//                }
                view.achievementRl.singleClick {
                    context?.startActivity<AchievementActivity>()
                }
            }
        }
    }

    inner class GameViewHolder(val view: ItemGameBinding) :
        MultipleViewHolder<TestPart>(view.root) {
        override fun setHolder(entity: TestPart) {
            view.gameIv.load(url)
//            view.gameTv.text = when(entity.picSrc){
//                1 -> "2048"
//                2 -> "拼图游戏"
//                3 -> "别踩白块"
//                else -> ""
//            }
            view.root.singleClick {
                when (entity.picSrc) {
                    1 -> context?.startActivity<FirstGameActivity>()
                    2 -> context?.startActivity<PuzzlesActivity>()
                    3 -> context?.startActivity<ThirdGameActivity>()
                    4 -> CommonServiceManager.service<AiService>()?.goGuessing(context)
                }
            }
            if (entity.picSrc == 5) {
                view.root.hide()
            }
            when (entity.picSrc) {
                1 -> view.gameIv.load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/01/c2c2d14e-bf43-41be-ab88-5d9a03e35371.jpg")
                2 -> view.gameIv.load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/01/d1824831-5d14-4dcb-b895-645718fdd2b8.jpg")
                3 -> view.gameIv.load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/01/36f9c0c1-0e03-448c-9ec3-e8e83b0f71f8.webp")
                4 -> {
                    Glide.with(view.gameIv)
                        .load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/15/e6344b42-1f11-4b4a-85a9-04ecdc3ce833.jpg")
                        .into(view.gameIv)
//                    view.gameIv.load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/14/2167d52a-cf27-41bc-825a-9aa1493390f4.jpg")
                }
            }
            view.gameIv.scaleType = ImageView.ScaleType.FIT_XY
        }
    }
}