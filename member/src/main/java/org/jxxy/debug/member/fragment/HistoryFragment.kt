package org.jxxy.debug.member.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.member.bean.PastRecords
import org.jxxy.debug.member.databinding.FragmentFollowBinding
import org.jxxy.debug.member.databinding.ItemHistoryBinding
import org.jxxy.debug.member.http.viewModel.MemberViewModel
import org.jxxy.debug.member.viewHolder.HistoryViewHolder

class HistoryFragment : BaseFragment<FragmentFollowBinding>() {
    val viewModel: MemberViewModel by lazy {
        ViewModelProvider(this).get(MemberViewModel::class.java)
    }
    val adapter = object : SingleTypeAdapter<PastRecords>() {
        override fun createViewHolder(
            viewType: Int,
            inflater: LayoutInflater,
            parent: ViewGroup
        ): RecyclerView.ViewHolder? = HistoryViewHolder(ItemHistoryBinding.inflate(inflater))
    }
    override fun bindLayout(): FragmentFollowBinding = FragmentFollowBinding.inflate(layoutInflater)

    override fun initView() {
        find.loadingViewLVCircularJump.setViewColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_100))
        find.loadingViewLVCircularJump.startAnim()
        find.listRecyclerView.adapter = adapter
        find.listRecyclerView.addItemDecoration(CommonItemDecoration(5f))
        viewModel.getMemberHistory()
    }

    override fun subscribeUi() {
        viewModel.historyDataLiveData.observe(this) {
            it.response?.let {
                it.list?.let { list ->
                    find.loadingViewLVCircularJump.stopAnim()
                    find.loadingViewLVCircularJump.gone()
                    find.listRecyclerView.show()
//                    find.titleToolbarLayout.show()
                    find.titleTextView.text="历史记录"

//                    find.titleToolbarLayout.setTitle("历史记录")
                    adapter.clearAndAdd(list)
                }
            }
        }
    }
}
