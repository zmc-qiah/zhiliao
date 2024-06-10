package org.jxxy.debug.test.fragment.activity

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.test.databinding.ActivitySpecialAnswerListBinding
import org.jxxy.debug.test.fragment.adapter.SpecialRvAdapter
import org.jxxy.debug.test.fragment.bean.TheBestScore
import org.jxxy.debug.test.fragment.viewModel.QuestionViewModel

class AnswerListActivity : BaseActivity<ActivitySpecialAnswerListBinding>() {

    val viewModel: QuestionViewModel by lazy {
        ViewModelProvider(this).get(QuestionViewModel::class.java)
    }

    override fun bindLayout(): ActivitySpecialAnswerListBinding {
        return ActivitySpecialAnswerListBinding.inflate(layoutInflater)
    }

    lateinit var adapter: SpecialRvAdapter
    var bestScore: TheBestScore? = null

    override fun initView() {
        val type = intent.getIntExtra("type", 0)
        if (intent.getStringExtra("pastTime") != null) {
            bestScore = TheBestScore(
                intent.getStringExtra("pastTime")!!,
                intent.getStringExtra("accuracy")!!,
                intent.getStringExtra("damage")!!
            )
        }
        if (bestScore != null) {
            adapter = SpecialRvAdapter(bestScore)
        } else {
            adapter = SpecialRvAdapter()
        }
        adapter.way = {
            finish()
        }
        adapter.fragmentManager = supportFragmentManager
        view.specialAnswerListRv.adapter = adapter
        view.specialAnswerListRv.layoutManager = LinearLayoutManager(this)
        view.specialAnswerListRv.addItemDecoration(CommonItemDecoration(10f))

    }

    override fun subscribeUi() {
        viewModel.getSpecialOutLiveData.observe(this) {
            it.onSuccess {
                it?.let {
                    adapter.run {
                        add(it.specialInfos)
                    }
                }
            }
            it.onError { error, response ->
            }
        }
        viewModel.getSpecialOut()
    }
}
