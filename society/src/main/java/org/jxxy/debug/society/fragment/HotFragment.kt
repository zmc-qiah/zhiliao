package org.jxxy.debug.society.fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.society.adapter.DiscussAdapter
import org.jxxy.debug.society.bean.Select
import org.jxxy.debug.society.util.SpaceItemDecoration
import org.jxxy.debug.society.databinding.FragementSelectBinding
import org.jxxy.debug.society.http.viewModel.SocialViewModel

class HotFragment: BaseFragment<FragementSelectBinding>() {

    private  val list=ArrayList<Select>()
    val viewModel: SocialViewModel by lazy {
        ViewModelProvider(this).get(SocialViewModel::class.java)
    }

    override fun bindLayout(): FragementSelectBinding {
        return FragementSelectBinding.inflate(layoutInflater)
    }

    override fun initView() {
        viewModel.DiscussgetAll()
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        find.recyclerRy.layoutManager=layoutManager
        find.recyclerRy.addItemDecoration(SpaceItemDecoration(40,0))

    }
    override fun subscribeUi() {
        viewModel.DiscussData .observe(this) { res ->
            res.onSuccess { r ->
                repeat(5){
                    list.addAll(r!!.discussInfos !!.map { Select(it!!.questionTitle!!,it.answerContent!!,it.questionLabel!!,it.id!!) })
                }


            }
            find.recyclerRy.adapter= DiscussAdapter(list)
        }




    }
}