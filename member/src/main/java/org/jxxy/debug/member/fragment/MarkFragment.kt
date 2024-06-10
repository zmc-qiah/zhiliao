package org.jxxy.debug.member.fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.common.R
import org.jxxy.debug.common.databinding.ItemResourceTextBinding
import org.jxxy.debug.common.databinding.ItemResourcesVideoBinding
import org.jxxy.debug.common.viewHolder.ResourceTextViewHolder
import org.jxxy.debug.common.viewHolder.ResourcesVideoViewHolder
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.member.databinding.FragmentFollowBinding
import org.jxxy.debug.member.http.viewModel.MemberViewModel


class MarkFragment : BaseFragment<FragmentFollowBinding>() {
    val viewModel: MemberViewModel by lazy {
        ViewModelProvider(this).get(MemberViewModel::class.java)
    }
    val adapter = object : MultipleTypeAdapter() {
        override fun createViewHolder(
            viewType: Int,
            inflater: LayoutInflater,
            parent: ViewGroup
        ): RecyclerView.ViewHolder? = when (viewType) {
            1 -> ResourceTextViewHolder(ItemResourceTextBinding.inflate(inflater))
            3 -> ResourcesVideoViewHolder(ItemResourcesVideoBinding.inflate(inflater))
            else -> null
        }
    }
    override fun bindLayout(): FragmentFollowBinding = FragmentFollowBinding.inflate(layoutInflater)
    override fun initView() {
        find.listRecyclerView.adapter = adapter
        find.listRecyclerView.addItemDecoration(CommonItemDecoration(15f))
        find.loadingViewLVCircularJump.setViewColor(ResourceUtil.getColor(R.color.primary_100))
        find.loadingViewLVCircularJump.startAnim()
        viewModel.getMemberMark()
    }

    override fun subscribeUi() {
        viewModel.markDataLiveData.observe(this) {
            Log.d("TAG", "subscriaabeUi: $it")
            it.response?.let {
                it.list?.let {
                        list ->
                    find.loadingViewLVCircularJump.stopAnim()
                    find.loadingViewLVCircularJump.gone()
                    find.titleTextView.text="收藏列表"
//                    find.titleToolbarLayout.setTitle("收藏列表")
                    adapter.clearAndAdd(list)
                    find.listRecyclerView.show()
//                    find.titleToolbarLayout.show()
                }
            }
        }
    }
}
