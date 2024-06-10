package org.jxxy.debug.member.fragment

import android.animation.ObjectAnimator
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.member.adapter.Weekly4Adapter
import org.jxxy.debug.member.bean.Weekly4Item
import org.jxxy.debug.member.databinding.FragmentWeekly4Binding
import org.jxxy.debug.member.http.respone.WeekReportResponce


class Weekly4Fragment(val info: WeekReportResponce) : BaseFragment<FragmentWeekly4Binding>() {
    override fun bindLayout(): FragmentWeekly4Binding = FragmentWeekly4Binding.inflate(layoutInflater)

    override fun initView() {
        val adapter = Weekly4Adapter()
        val fields = info.weekReportInfo!!::class.java.declaredFields
        for (i in 0 until fields.size step 2){
            val field = fields[i]
            field.isAccessible =true
            val field1 = fields[i+1]
            field1.isAccessible =true
            adapter.add(Weekly4Item(getName(field.name), 0))
            adapter.add(Weekly4Item(getName(field1.name), 0))
            adapter.add(Weekly4Item(field.get(info.weekReportInfo).toString(), 1))
            adapter.add(Weekly4Item(field1.get(info.weekReportInfo).toString(), 1))
        }

        find.weekly4.adapter = adapter
        val fadeInAnimator = ObjectAnimator.ofFloat(find.weekly4, "alpha", 0f, 1f)
        fadeInAnimator.duration = 1000 // 设置动画持续时间，单位为毫秒
        fadeInAnimator.start()
    }

    override fun subscribeUi() {
    }
    fun getName(name: String): String = when (name) {
        "studyTime" -> "学习时间"
        "dayStudyTime" -> "日均学习时间"
        "studyTask" -> "本周学习任务"
        "noFinishPoint" -> "未完成小任务点"
        "getScore" ->"本周获得积分"
        "consumeScore" -> "本周消费积分"
        "answerSum"  -> "本周答题数目"
        "answerRight"   ->"答题正确率"
        "readSum" ->"阅读文章数量"
        "videoTime" ->" 观看视频时长 "
        "aiTime" ->"与AI交互"
        "aiStudyTime" -> "学习AI时长"
        else -> ""
    }
}
