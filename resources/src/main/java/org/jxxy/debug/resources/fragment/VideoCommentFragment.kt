package org.jxxy.debug.resources.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.resources.CommentDialog
import org.jxxy.debug.resources.bean.Comment
import org.jxxy.debug.resources.databinding.FragmentVideoCommentBinding
import org.jxxy.debug.resources.databinding.ItemCommentBinding
import org.jxxy.debug.resources.http.response.CommentResponse
import org.jxxy.debug.resources.http.viewModel.ResourceViewModel
import org.jxxy.debug.resources.videoHelper.MyHelperCommentResponse
import org.jxxy.debug.resources.viewHolder.VideoCommentViewHolder

class VideoCommentFragment(val resourceId: Int) : BaseFragment<FragmentVideoCommentBinding>() {
    val viewModel: ResourceViewModel by lazy {
        ViewModelProvider(this).get(ResourceViewModel::class.java)
    }
    var start = 1
    val page = 30
    var isEnd = false
    var flag =false
    val size: Int
        get() {
            return adapter.itemCount
        }
    var isFullPage: Boolean = false
    val adapter = object : SingleTypeAdapter<Comment>() {
        override fun createViewHolder(
            viewType: Int,
            inflater: LayoutInflater,
            parent: ViewGroup
        ): RecyclerView.ViewHolder? = VideoCommentViewHolder(ItemCommentBinding.inflate(inflater, parent, false))
    }
    override fun bindLayout(): FragmentVideoCommentBinding = FragmentVideoCommentBinding.inflate(layoutInflater)

    override fun initView() {
        find.smartRefreshLayout.setEnableRefresh(false)
        find.smartRefreshLayout.setEnableLoadMore(true)
        find.smartRefreshLayout.setRefreshFooter(ClassicsFooter(context))
        find.smartRefreshLayout.setOnLoadMoreListener {
            viewModel.getComment(resourceId, start++, page)
        }
        if (resourceId != -1) {
            viewModel.getComment(resourceId, start++, page)
        }
        find.recycleView.adapter = adapter
        find.recycleView.addItemDecoration(CommonItemDecoration(15f))
        find.phoneET.setOnFocusChangeListener(object:OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus){
                    val fragment = CommentDialog(resourceId,object :MyHelperCommentResponse{
                        override fun help(response: CommentResponse) {
                            find.recycleView.smoothScrollToPosition(adapter.itemCount)
                            find.smartRefreshLayout.autoLoadMore()
                            find.phoneET.hint = "快来留下评论吧"
                            find.emptyCommentTextView.gone()
                        }
                    })
                    find.phoneET.hint = "评论输入中"
                    find.phoneET.clearFocus()
                    fragment.show(childFragmentManager,fragment.tag)
                }
            }
        })
        find.phoneET.singleClick {
            val fragment = CommentDialog(resourceId,object :MyHelperCommentResponse{
                override fun help(response: CommentResponse) {
                    find.smartRefreshLayout.autoLoadMore()
                }
            })
            fragment.show(childFragmentManager,fragment.tag)
            it.clearFocus()
        }
        if (flag){
            (find.root.layoutParams as ViewGroup.MarginLayoutParams).topMargin =80
        }
    }

    override fun subscribeUi() {
        viewModel.commentLiveData.observe(this) {
            it.onSuccess {
                it?.let {
                    val a = adapter.itemCount
                    if (it.comment.size > 0) {
                        if (it.myPage.current * page <= it.myPage.total) {
                            adapter.add(it.comment)
                        } else {
                            start -= 1
                            adapter.add(it.comment, true)
                        }
                        find.recycleView.smoothScrollToPosition(a+1)
                        find.emptyCommentTextView.gone()
                    }
                    find.smartRefreshLayout.finishLoadMore()
                }
            }
        }
    }
    fun <T> SingleTypeAdapter<T>.add(comment: ArrayList<Comment>?, b: Boolean) {
        comment?.let {
            val begin = itemCount - ((start - 1) * page)
            val end = it.size - 1
            for (i in begin..end) {
                adapter.add(comment[i])
            }
        }
    }
}
