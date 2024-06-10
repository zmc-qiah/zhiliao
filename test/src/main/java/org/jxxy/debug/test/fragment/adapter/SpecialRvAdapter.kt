package org.jxxy.debug.test.fragment.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.test.R
import org.jxxy.debug.test.databinding.ItemSpecialBinding
import org.jxxy.debug.test.fragment.activity.QuestionActivity
import org.jxxy.debug.test.fragment.activity.TheChartsActivity
import org.jxxy.debug.test.fragment.bean.SpecialOut
import org.jxxy.debug.test.fragment.bean.TheBestScore
import org.jxxy.debug.test.fragment.fragment.TheBestScoreDialog

class SpecialRvAdapter(val bestScore: TheBestScore? = null) : SingleTypeAdapter<SpecialOut>() {
    lateinit var fragmentManager: FragmentManager
    var parentWidth: Int = 0
    var way: () -> Unit = {}
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {
        return SpecialQuestionViewHolder(ItemSpecialBinding.inflate(inflater, parent, false))
    }

    inner class SpecialQuestionViewHolder(view: ItemSpecialBinding) :
        SingleViewHolder<ItemSpecialBinding, SpecialOut>(view) {
        override fun setHolder(entity: SpecialOut) {
            view.doSumTv.text =
                "已有${entity.peopleNums.substring(0, entity.peopleNums.lastIndex - 2)}位勇士挑战过该boss"
//            view.hotTv.text = entity.peopleNums
            view.specialQuestionSumTv.text = entity.questionNums
            val sumString =
                entity.questionNums.substring(0, entity.questionNums.length - 2).substring(3)
            val sum = sumString.toInt()
//            Glide.with(view.specialIv)
//                .load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/16/3e05ab13-6084-4002-b00a-be64528cf759.png")
//                .into(view.specialIv)
            Glide.with(view.doSumIv)
                .load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/15/e3af5751-62c2-49d8-a96a-bef8cafe03bd.png")
                .into(view.doSumIv)
//            view.BossIv.load("https://tse3-mm.cn.bing.net/th/id/OIP-C.mNo_uP7IZiZfs3X5pUqUrgAAAA?w=164&h=81&c=7&r=0&o=5&dpr=1.3&pid=1.7")
//            Glide.with(view.BossIv).load(entity.photo).into(view.BossIv)
            view.BossIv.load(entity.photo, 5)
            view.specialCheckTv.singleClick(2000) {
                val intent = Intent(context, QuestionActivity::class.java)
                intent.putExtra("sum", sum)
                intent.putExtra("type", AnswerRvAdapter.SPECIAL)
                intent.putExtra("name", entity.name)
                Log.d("TTTTTTTTTT", "${entity.name}")
                context.startActivity(intent)
                way.invoke()
            }
            view.theChartsIcon.singleClick {
                val intent = Intent(context, TheChartsActivity::class.java)
                intent.putExtra(
                    "type", when (entity.name) {
                        "基础概念" -> 1
                        "应用技术" -> 2
                        "教育发展" -> 3
                        "伦理道德" -> 4
                        else -> 0
                    }
                )
                context?.startActivity(intent)
            }
            view.theBestScoreIcon.singleClick {
                if (bestScore == null) {
                    TheBestScoreDialog(
                        TheBestScore(
                            entity.bestGrades.pastTime,
                            entity.bestGrades.accuracy,
                            entity.bestGrades.damage
                        )
                    ).show(fragmentManager)
                } else {
                    TheBestScoreDialog(
                        TheBestScore(
                            bestScore.pastTime,
                            bestScore.accuracy,
                            bestScore.damage
                        )
                    ).show(fragmentManager)
                }
            }
//            view.bloodTrueView.layoutParams = ViewGroup.LayoutParams(
//                (getWidth() * 0.9 * 0.5).toInt(),
//                view.bloodTrueView.height
//            )
//            view.bloodTrueView.layoutParams.width = (view.bloodBottomView.layoutParams.width * 0.5).toInt()
            view.BossIv.post {
                parentWidth = view.BossIv.width
                val layoutParams = view.bloodTrueView.layoutParams
                view.bloodPercentTv.text = entity.hp
                val percent = entity.hp.substring(0, entity.hp.lastIndex).toInt()
                view.bloodTrueView.setBackgroundResource(
                    when {
                        percent >= 75 -> R.drawable.view_blood_true_first
                        percent >= 50 -> R.drawable.view_blood_true_second
                        percent >= 25 -> R.drawable.view_blood_true_third
                        else -> R.drawable.view_blood_true_fourth
                    }

                )
                layoutParams.width = (parentWidth * percent * 1.0 / 100).toInt()
                view.bloodTrueView.layoutParams = layoutParams
            }
//            if (entity.questionText.length >= 20) {
//                view.specialQuestionContentsTv.text = entity.questionText.substring(0, 20).replace("\r","").replace("\n","") + "..."
//            }else{

            view.specialQuestionContentsTv.text = entity.name
//                .replace("\r","").replace("\n","")
//            }
        }
    }
}