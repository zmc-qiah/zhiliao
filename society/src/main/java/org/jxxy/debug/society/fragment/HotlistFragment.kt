package org.jxxy.debug.society.fragment

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.society.adapter.HotlistAdapter
import org.jxxy.debug.society.bean.HotlistBean
import org.jxxy.debug.society.databinding.FragmentHotlistBinding
import org.jxxy.debug.society.http.viewModel.SocialViewModel
import org.jxxy.debug.society.util.SpaceItemDecoration

class HotlistFragment: BaseFragment<FragmentHotlistBinding>() {
    val viewModel: SocialViewModel by lazy {
        ViewModelProvider(this).get(SocialViewModel::class.java)
    }
    private  val list=ArrayList<HotlistBean>()
    override fun bindLayout(): FragmentHotlistBinding {
     return FragmentHotlistBinding.inflate(layoutInflater)

    }

    override fun initView() {
        viewModel.rankQuestion()
       val layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        find.hotlistRy.layoutManager=layoutManager
        find.hotlistRy.addItemDecoration(SpaceItemDecoration(30,0))


    }

    override fun subscribeUi() {
        viewModel.rankQuestionData.observe(this) { res ->
            res.onSuccess { r ->
                var numrank=1
                repeat(5){
                list.addAll(r!!.discussQuestionInfos!!.mapIndexed { index, it ->
                    HotlistBean(numrank++, it!!.questionTitle!!, it.question_hot!!, it.questionContent!!, it.questionPhoto!!)
                })}
            }

            find.hotlistRy.adapter = HotlistAdapter(list)
        }
    }

}
