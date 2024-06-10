package org.jxxy.debug.resources.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.common.databinding.ItemResourceTextBinding
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.resources.bean.RecommendInfo
import org.jxxy.debug.resources.databinding.FragmentRecommendBinding
import org.jxxy.debug.resources.http.viewModel.ResourceViewModel
import org.jxxy.debug.resources.viewHolder.VideoRecommendViewHolder

class RecommendFragment() : BaseFragment<FragmentRecommendBinding>() {
    val adapter = object : SingleTypeAdapter<RecommendInfo>() {
        override fun createViewHolder(
            viewType: Int,
            inflater: LayoutInflater,
            parent: ViewGroup
        ): RecyclerView.ViewHolder? = VideoRecommendViewHolder(ItemResourceTextBinding.inflate(inflater, parent, false))
    }
    val viewModel by lazy {
        ViewModelProvider(this).get(ResourceViewModel::class.java)
    }
    override fun bindLayout(): FragmentRecommendBinding = FragmentRecommendBinding.inflate(layoutInflater)

    override fun initView() {
        find.recycleView.adapter = adapter
        find.recycleView.addItemDecoration(CommonItemDecoration(10f))
        viewModel.getRecommend()
    }

    override fun subscribeUi() {
        viewModel.recommendLiveData.observe(this) {
            it.onSuccess {
                it?.let {
                    adapter.add(it.recommendInfos)
                }
            }
        }
    }
}
