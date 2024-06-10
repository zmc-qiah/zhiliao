package org.jxxy.debug.test.fragment

import android.content.Context
import android.content.Intent
import com.google.auto.service.AutoService
import org.jxxy.debug.common.service.TestServiceCommon
import org.jxxy.debug.test.fragment.activity.QuestionActivity
import org.jxxy.debug.test.fragment.adapter.AnswerRvAdapter

@AutoService(TestServiceCommon::class)
class TestServiceImpl : TestServiceCommon {
    override fun goDailyAnswer(context: Context) {
        val intent = Intent(context, QuestionActivity::class.java)
        intent.putExtra("sum", 5)
        intent.putExtra("type", AnswerRvAdapter.ANSWER)
        context.startActivity(intent)
    }
}