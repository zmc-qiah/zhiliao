package org.jxxy.debug.test.fragment.activity

import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.test.databinding.ActivityStudyPlanBinding


class StudyPlanActivity : BaseActivity<ActivityStudyPlanBinding>() {
    override fun bindLayout(): ActivityStudyPlanBinding {
        return ActivityStudyPlanBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val text = "我们希望，瞬间的积淀不要流淌，岁月的馈赠别被消磨，而是在时间的河床上凝聚起沉潜的力量，让我们与时代和社会一起，向阳生长。"
        val spannableString = SpannableString(text)
        spannableString.setSpan(RelativeSizeSpan(3f),0,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        view.studyPlanSayingTv.text = spannableString
    }

    override fun subscribeUi() {

    }
}