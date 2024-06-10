package org.jxxy.debug.member.fragment

import androidx.lifecycle.ViewModelProvider
import org.jxxy.debug.common.R
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.member.adapter.FollowAdapter
import org.jxxy.debug.member.databinding.FragmentFollowBinding
import org.jxxy.debug.member.http.viewModel.MemberViewModel

class FollowFragment : BaseFragment<FragmentFollowBinding>() {
    val viewModel: MemberViewModel by lazy {
        ViewModelProvider(this).get(MemberViewModel::class.java)
    }
    val followAdapter by lazy { FollowAdapter() }
    override fun bindLayout(): FragmentFollowBinding {
        return FragmentFollowBinding.inflate(layoutInflater)
    }

    override fun initView() {
        find.listRecyclerView.adapter = followAdapter
        find.listRecyclerView.addItemDecoration(CommonItemDecoration(8f))
        find.loadingViewLVCircularJump.setViewColor(ResourceUtil.getColor(R.color.primary_100))
        find.loadingViewLVCircularJump.startAnim()
        viewModel.getMemberFellow()
    }

    override fun subscribeUi() {
        viewModel.fellowDataLiveData.observe(this) {
            it?.response?.let { list ->
                find.loadingViewLVCircularJump.stopAnim()
                find.loadingViewLVCircularJump.gone()
//                find.titleToolbarLayout.setTitle("关注列表")
                find.titleTextView.text = "关注列表"
                followAdapter.add(list.list)
            }
        }
    }
}
