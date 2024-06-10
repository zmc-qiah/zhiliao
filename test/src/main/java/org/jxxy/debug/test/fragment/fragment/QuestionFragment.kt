package org.jxxy.debug.test.fragment.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.test.databinding.FragmentQuesionBinding
import org.jxxy.debug.test.fragment.adapter.QuestionRvAdapter
import org.jxxy.debug.test.fragment.bean.Question
import org.jxxy.debug.test.fragment.viewModel.QuestionViewModel

class QuestionFragment(
    val questions: ArrayList<Question>,
    val index: Int,
    val viewModel: QuestionViewModel,
    val choose : String? = null
) : BaseFragment<FragmentQuesionBinding>() {

    val list: ArrayList<String> by lazy {
        ArrayList()
    }

    override fun bindLayout(): FragmentQuesionBinding {
        return FragmentQuesionBinding.inflate(layoutInflater)
    }

    override fun initView() {
        init()
        val adapter = QuestionRvAdapter(
            questions,
            questions[index].questionType,
            index,
            parentFragmentManager,
            viewModel,
            choose
        )
        find.questionRv.adapter = adapter
        find.questionRv.addItemDecoration(CommonItemDecoration(10f))
        find.questionRv.layoutManager = LinearLayoutManager(this.context)
        viewModel.collectionState.value = questions[index].collectState ?: 0
//        viewModel.type.observe(this) {
//            when (questions.type) {
//                QuestionRvAdapter.SINGLE_SELECTION -> if (it == QuestionRvAdapter.TAKE) {
//                    adapter.run {
//                        finishView.chooseOptionTv.show()
//                        if (isDone) {
//                            options[nowChoose[0]].setBackgroundResource(R.drawable.button_option_wrong)
//                            options[trueAnswer[0]].setBackgroundResource(R.drawable.button_option_true)
//                        }else{
//                            if(nowChoose.size == 0) {
//
//                            }
//                        }
//                    }
//                } else if (it == QuestionRvAdapter.RECITE) {
//                    adapter.run {
//                        finishView.root.show()
//                        finishView.chooseOptionTv.hide()
//
//                    }
//                }
//            }
//        }
    }

    override fun subscribeUi() {
    }

    fun init() {
    }
}
