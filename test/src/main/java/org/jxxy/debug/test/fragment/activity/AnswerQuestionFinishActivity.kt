package org.jxxy.debug.test.fragment.activity

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.test.databinding.ActivityAnswerQuestionFinishBinding
import org.jxxy.debug.common.service.goMainActivity
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.test.fragment.bean.Achievement
import org.jxxy.debug.test.fragment.fragment.DoneAchievementDialog
import org.jxxy.debug.test.fragment.viewModel.AchievementViewModel

class AnswerQuestionFinishActivity : BaseActivity<ActivityAnswerQuestionFinishBinding>() {
    val viewModel: AchievementViewModel by lazy {
        ViewModelProvider(this).get(AchievementViewModel::class.java)
    }
    var damage: Double = -1.0
    var nowDone = 0
    lateinit var achievement1: Achievement
    lateinit var achievement2: Achievement
    lateinit var achievement3: Achievement
    override fun bindLayout(): ActivityAnswerQuestionFinishBinding {
        return ActivityAnswerQuestionFinishBinding.inflate(layoutInflater)
    }


    override fun initView() {
        val time = intent.getLongExtra("time", 0) / 1000
        val right = intent.getIntExtra("right", 0)
        val wrong = intent.getIntExtra("wrong", 0)
        val type = intent.getIntExtra("type", 0)
        var damageString: String = ""
        val minutes = time / 60
        val seconds = time % 60
        val result = String.format("%02d:%02d", minutes, seconds)
        val percent = String.format("%2.2f", right.toDouble() / (right + wrong) * 100)
        view.useTimeTv.text = result
        view.rightTv.text = right.toString()
        view.percentTv.text = percent
        if(type != null && type != 0){
            damage = when {
                time < 120 -> 5
                time < 180 -> 3
                else -> 1
            } + right.toDouble() / (right + wrong) * 10
            damageString = String.format("%2.2f", damage)
            view.dayUnitTv.text = "%"
            view.dayTv.text = damageString
            view.dayTvTitle.text = "造成伤害"
        }else{
            view.dayTv.text = "15"
        }
//        val content = SpannableStringBuilder().apply {
//            append("本次答题用时")
//            append(
//                "$result",
//                RelativeSizeSpan(1.5f),
//                SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//            append("\n")
//            append("答对了")
//            append(
//                "${right}",
//                RelativeSizeSpan(1.5f),
//                SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//            append("题\n")
//            append("正确率为")
//            append(
//                "$percent",
//                RelativeSizeSpan(1.5f),
//                SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//            append("%")
//            if (type != null && type != 0) {
//                damage = when {
//                    time < 120 -> 5
//                    time < 180 -> 3
//                    else -> 1
//                } + right.toDouble() / (right + wrong) * 10
//                damageString = String.format("%2.2f", damage)
//                append("\n")
//                append("造成了")
//                append(
//                    "${damageString}",
//                    RelativeSizeSpan(1.5f),
//                    SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
//                )
//                append("%的伤害")
//            }


//        val content = SpannableString("本次答题用时$result\n答对了${right}题\n正确率为${percent}%")
        view.finishBt.singleClick {
            if (type == null || type == 0) {
                goMainActivity(this,2)
            } else {
                val intent = Intent(this, AnswerListActivity::class.java)
                intent.putExtra("pastTime", result)
                intent.putExtra("accuracy", percent)
                intent.putExtra("damage", damageString)
                startActivity(intent)
            }
            finish()
        }
    }

    override fun subscribeUi() {
        val list = ArrayList<Int>()
        if (damage >= 10) {
            list.add(16)
            list.add(15)
            list.add(14)
            nowDone = 16
        } else if (damage >= 5) {
            list.add(15)
            list.add(14)
            nowDone = 15
        } else if (damage >= 2) {
            list.add(14)
            nowDone = 14
        }
        viewModel.doneAchievement(list)
//        viewModel.getAchievementLiveData.observe(this) {
//            it.onSuccess {
//                it?.let {
//                    for (achievement in it.achievementInfoList) {
//                        if (achievement.id == 14) {
//                            achievement1 = achievement
//                        } else if (achievement.id == 15) {
//                            achievement2 = achievement
//                        } else if (achievement.id == 16) {
//                            achievement3 = achievement
//                        }
//                    }
//
//                }
//            }
//        }
        viewModel.doneAchievementLiveData.observe(this) {
            it.onSuccess {
                it?.let {
                    for (achievement in it.achievementInfoList) {
                        if (achievement.id == 16) {
                            achievement1 = achievement
                        } else if (achievement.id == 15) {
                            achievement2 = achievement
                        } else if (achievement.id == 14) {
                            achievement3 = achievement
                        }
                    }
                    if (nowDone >= 16 ) {
                        DoneAchievementDialog(achievement1).show(supportFragmentManager)
                    }
                    if (nowDone >= 15 ) {
                        DoneAchievementDialog(achievement2).show(supportFragmentManager)
                    }
                    if (nowDone >= 14 ) {
                        DoneAchievementDialog(achievement3).show(supportFragmentManager)
                    }
                }
            }
            viewModel.getAchievement()
        }
        viewModel.userInfoDataLiveData.observe(this){
            it.onSuccess {
                it?.let {
                    view.userIv.load(it.headPhoto,true)
                    view.userTv.text = it?.userName
                }
            }
        }
        viewModel.getInfo()
    }

    }