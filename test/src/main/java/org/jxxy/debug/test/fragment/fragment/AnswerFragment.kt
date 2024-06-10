package org.jxxy.debug.test.fragment.fragment

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.jxxy.debug.corekit.common.BaseFragment

import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.test.databinding.FragmentAnswerBinding
import org.jxxy.debug.test.fragment.adapter.SpecialRvAdapter
import org.jxxy.debug.test.fragment.bean.QuestionContext
import org.jxxy.debug.test.fragment.viewModel.QuestionViewModel

class AnswerFragment(val type:Int) : BaseFragment<FragmentAnswerBinding>() {
    val list : ArrayList<QuestionContext> by lazy {  ArrayList() }
    val viewModel:QuestionViewModel by lazy {
        ViewModelProvider(this).get(QuestionViewModel::class.java)
    }
    var start = 1
    var pageSize = 5
    lateinit var category:String
    val adapter by lazy {
//        SpecialRvAdapter(type)
    }
    override fun bindLayout(): FragmentAnswerBinding {
        return FragmentAnswerBinding.inflate(layoutInflater)
    }

    override fun initView() {
        category = when(type){
            5 -> "基础概念"
            6 -> "应用技术"
            7 -> "教育发展"
            8 -> "伦理道德"
            else -> ""
        }
//        find.answerFragRv.adapter = adapter
        find.refreshLayout.setOnLoadMoreListener {
            viewModel.getSpecialQuestion(category,start,pageSize)
            find.refreshLayout.finishLoadMore()
        }
        find.refreshLayout.setEnableRefresh(false)
        find.answerFragRv.addItemDecoration(CommonItemDecoration(10f))
        find.answerFragRv.layoutManager = LinearLayoutManager(this.context)

    }

    override fun subscribeUi() {
        viewModel.getSpecialQuestionLiveData.observe(this){
            it.onSuccess {
                it?.let {
//                    adapter.add(it.questionSimpleInfos)
                    start++
                }
            }
            it.onError { error, response ->
                Toast.makeText(context,"没有更多题目了",Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.getSpecialQuestion(category,start,pageSize)
    }

    fun init(){

    }
}