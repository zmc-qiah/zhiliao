package org.jxxy.debug.member.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.luck.picture.lib.entity.LocalMedia
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.mmkv.PersistenceUtil
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.member.bean.Forum
import org.jxxy.debug.member.databinding.FragmentForumGroupBinding
import org.jxxy.debug.member.databinding.ItemForumBinding
import org.jxxy.debug.member.http.viewModel.MemberViewModel
import org.jxxy.debug.member.myListener.ForumClickListener
import org.jxxy.debug.member.myListener.ModifyDialogListener
import org.jxxy.debug.member.viewHolder.ForumViewHolder

class ForumFragment(val type:Int) : BaseFragment<FragmentForumGroupBinding>() {
    val TAG = "ForumFragment"
    val viewModel: MemberViewModel by lazy {
        ViewModelProvider(this).get(MemberViewModel::class.java)
    }
    val adapter = object : SingleTypeAdapter<Forum>() {
        override fun createViewHolder(
            viewType: Int,
            inflater: LayoutInflater,
            parent: ViewGroup
        ): RecyclerView.ViewHolder? = ForumViewHolder(ItemForumBinding.inflate(inflater), parentFragmentManager,clickListener)
    }
    val clickListener = object : ForumClickListener{
        override fun clickET() {
            val modifyDialog = ModifyDialog()
            modifyDialog.listener = object :ModifyDialogListener{
                override fun submit(text: String, list: List<LocalMedia>) {
                }
            }
            modifyDialog.show(parentFragmentManager,modifyDialog.tag)
        }
    }
    var start = 1
    val page = 6
    var isEnd = false
    var flag =false
    val size: Int
        get() {
            return adapter.itemCount
        }
    val value :String?
        get(){
            return PersistenceUtil.getValue<String>("userName")
        }
    constructor():this(0)

    override fun bindLayout(): FragmentForumGroupBinding = FragmentForumGroupBinding.inflate(layoutInflater)

    override fun initView() {
        find.forumRecycleView.adapter = adapter
        find.forumRecycleView.addItemDecoration(CommonItemDecoration(10f))
        find.smartRefreshLayout.setEnableRefresh(false)
        find.smartRefreshLayout.setEnableLoadMore(true)
        find.smartRefreshLayout.setRefreshFooter(ClassicsFooter(context))
        find.smartRefreshLayout.setOnLoadMoreListener {
            viewModel.getForum(start++, page)
        }
    }
    override fun subscribeUi() {
        viewModel.forumDataLiveData.observe(this) {
            it.onSuccess {res->
                if(res?.list?.size == 0){
                    "没有更多了".toast()
                }else{
                    if (type == 1){
                        res?.list?.forEach {forum->
                            if(value.equals(forum.userName)){
                                find.errorTextView.gone()
                                adapter.add(forum)
                            }
                        }
                    }else{
                        find.errorTextView.gone()
                        adapter.add(res?.list)
                    }
                }
                find.smartRefreshLayout.finishLoadMore()
            }
        }
        viewModel.getForum(start++,page)
    }
    fun <T> SingleTypeAdapter<T>.add(forums: ArrayList<Forum>?, b: Boolean) {
        forums?.let {
            val begin = itemCount - ((start - 1) * page)
            val end = it.size - 1
            for (i in begin..end) {
                if (type == 1){
                    if(value.equals(forums[i].userName)){
                        find.errorTextView.gone()
                        adapter.add(forums[i])
                    }
                }else{
                    adapter.add(forums[i])
                }
            }
        }
    }
}
