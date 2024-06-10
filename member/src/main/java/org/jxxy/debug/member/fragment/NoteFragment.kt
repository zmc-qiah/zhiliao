package org.jxxy.debug.member.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.common.service.drawThinkMapNoAI
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.member.databinding.FragmentFollowBinding
import org.jxxy.debug.member.databinding.ItemNoteBinding
import org.jxxy.debug.member.databinding.ItemPreviewBinding
import org.jxxy.debug.member.http.viewModel.MemberViewModel
import org.jxxy.debug.member.util.MyType
import org.jxxy.debug.member.viewHolder.NoteViewHolder
import org.jxxy.debug.member.viewHolder.PreViewViewHolder

class NoteFragment : BaseFragment<FragmentFollowBinding>() {
    val viewModel: MemberViewModel by lazy {
        ViewModelProvider(this).get(MemberViewModel::class.java)
    }
    val adapter = object : MultipleTypeAdapter() {
        override fun createViewHolder(
            viewType: Int,
            inflater: LayoutInflater,
            parent: ViewGroup
        ): RecyclerView.ViewHolder? = when(viewType){
            MyType.PREVIEW ->{
                PreViewViewHolder(ItemPreviewBinding.inflate(inflater))
            }
            else ->{
                NoteViewHolder(ItemNoteBinding.inflate(inflater))
            }
        }
    }
    var noteSize:Int = 0
    var start:Int = 0
    override fun bindLayout(): FragmentFollowBinding = FragmentFollowBinding.inflate(layoutInflater)

    override fun initView() {
        find.listRecyclerView.adapter = adapter
        find.addBtn.show()
        find.listRecyclerView.addItemDecoration(CommonItemDecoration(10f))
        find.loadingViewLVCircularJump.setViewColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_100))
        find.loadingViewLVCircularJump.startAnim()
        find.addBtn.singleClick {
            context?.let { it1 -> drawThinkMapNoAI(it1) }
        }
        viewModel.getMemberNote()
    }
    override fun subscribeUi() {
        viewModel.noteDataLiveData.observe(this) {
            it.response?.let {
                it.list?.let { list ->
                    noteSize = list.size
                    find.loadingViewLVCircularJump.stopAnim()
                    find.loadingViewLVCircularJump.gone()
                    find.titleTextView.text="学习笔记"
                    adapter.clearAndAdd(list)
                    viewModel.getPreview(start++)
                }
            }
        }
        viewModel.previews.observe(this){
            it.onSuccess {
                it?.let {
                    adapter.add(it)
                    // 一次15条写死了
                    if (it.size == 15){
                        viewModel.getPreview(start++)
                    }
                }
            }
        }
    }
}
