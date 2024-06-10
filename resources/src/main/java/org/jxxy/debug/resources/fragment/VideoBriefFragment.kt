package org.jxxy.debug.resources.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.common.databinding.ItemResourceTextBinding
import org.jxxy.debug.common.databinding.ItemResourcesTextBinding
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.corekit.util.nullOrNot
import org.jxxy.debug.resources.bean.MyState
import org.jxxy.debug.resources.bean.ResourceInfo
import org.jxxy.debug.resources.bean.VideoIcon
import org.jxxy.debug.resources.databinding.FragmentVideoBinding
import org.jxxy.debug.resources.databinding.ItemVideoBriefBinding
import org.jxxy.debug.resources.databinding.ItemVideoIconBinding
import org.jxxy.debug.resources.http.response.ResourceInfoResponse
import org.jxxy.debug.resources.http.viewModel.ResourceViewModel
import org.jxxy.debug.resources.util.MyUtil
import org.jxxy.debug.resources.util.Mytype
import org.jxxy.debug.resources.viewHolder.VideoBriefViewHolder
import org.jxxy.debug.resources.viewHolder.VideoIconViewHolder
import org.jxxy.debug.resources.viewHolder.VideoRecommendViewHolder

class VideoBriefFragment(val entry: ResourceInfoResponse) : BaseFragment<FragmentVideoBinding>() {
    val viewModel by lazy {
        ViewModelProvider(this).get(ResourceViewModel::class.java)
    }
    var flag:Boolean = false
    val adapter by lazy {
        object : MultipleTypeAdapter() {
            override fun createViewHolder(
                viewType: Int,
                inflater: LayoutInflater,
                parent: ViewGroup
            ): RecyclerView.ViewHolder? = when (viewType) {
                Mytype.RESOURCE_VIDEO_INFO -> VideoBriefViewHolder(ItemVideoBriefBinding.inflate(inflater, parent, false))
                Mytype.ITEM_VIDEO_ICON -> VideoIconViewHolder(ItemVideoIconBinding.inflate(inflater, parent, false), viewModel, this@VideoBriefFragment)
                Mytype.ITEM_VIDEO_RECOMMEND -> VideoRecommendViewHolder(ItemResourceTextBinding.inflate(inflater, parent, false))
                else -> { null }
            }
        }
    }
    override fun bindLayout(): FragmentVideoBinding = FragmentVideoBinding.inflate(layoutInflater)

    override fun initView() {
        find.recycleView.adapter = adapter
        find.recycleView.addItemDecoration(CommonItemDecoration(10f))
        entry.resourceInfo.nullOrNot(
            {
                val resourceInfo = ResourceInfo()
                resourceInfo.resourceId = Mytype.TYPE_ERROR
                resourceInfo.resourceContent = MyUtil.url
                resourceInfo.resourceAuthorName = "未知作者"
                resourceInfo.createTime = "未知时间"
                resourceInfo.resourceLabel = "未知标签"
                resourceInfo.resourceReads = 0
                resourceInfo.resourceComments = 0
                resourceInfo.resourceTitle = "未知标题"
                adapter.add(resourceInfo)
            },
            { adapter.add(it) }
        )
        entry.myState.nullOrNot(
            {
                val videoIcon = VideoIcon(MyState(), entry.resourceInfo?.resourceLikes ?: -1, entry.resourceInfo?.resourceId ?: -1)
                adapter.add(videoIcon)
            },
            {
                val videoIcon = VideoIcon(it, entry.resourceInfo?.resourceLikes ?: -1, entry.resourceInfo?.resourceId ?: -1)
                adapter.add(videoIcon)
            }
        )
        viewModel.getRecommend()
    }

    override fun subscribeUi() {
        viewModel.recommendLiveData.observe(this) {
            it.onSuccess {
                it?.let {
                    it.recommendInfos?.let {
                        adapter.add(it)
                    }
                }
            }
        }
    }
}
