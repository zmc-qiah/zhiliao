package org.jxxy.debug.test.fragment.activity

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.test.databinding.ActivityWrongBinding
import org.jxxy.debug.test.fragment.adapter.AnswerRvAdapter
import org.jxxy.debug.test.fragment.bean.CollectionOrMistakeQuestion
import org.jxxy.debug.test.fragment.viewModel.QuestionViewModel

class WrongActivity : BaseActivity<ActivityWrongBinding>() {

    override fun bindLayout(): ActivityWrongBinding {
        return ActivityWrongBinding.inflate(layoutInflater)
    }
    val list : ArrayList<CollectionOrMistakeQuestion> by lazy {
        ArrayList()
    }
    val viewModel : QuestionViewModel by lazy{
        ViewModelProvider(this).get(QuestionViewModel::class.java)
    }
    lateinit var adapter : AnswerRvAdapter


    override fun initView() {
        adapter = AnswerRvAdapter(AnswerRvAdapter.WRONG,viewModel)
        view.wrongRv.adapter = adapter
        view.wrongRv.addItemDecoration(CommonItemDecoration(10f))
        view.wrongRv.layoutManager = LinearLayoutManager(this)
    }

    override fun subscribeUi() {
        viewModel.getMistakeLiveData.observe(this){
            it.onSuccess {
                it?.let {
                    adapter.clearAndAdd(it.collections)
                    adapter.notifyItemRangeChanged(0,it.collections.size)
                }
            }
        }
        viewModel.getMistake()
    }

    fun init(){
    }

    override fun onResume() {
        viewModel.getMistake()
        super.onResume()
    }
}