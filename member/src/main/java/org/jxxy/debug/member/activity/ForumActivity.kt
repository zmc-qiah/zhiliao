package org.jxxy.debug.member.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.luck.picture.lib.entity.LocalMedia
import org.jxxy.debug.common.service.loginCheck
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.member.bean.Forum
import org.jxxy.debug.member.databinding.ActivityForumBinding
import org.jxxy.debug.member.databinding.ItemForumActivityBinding
import org.jxxy.debug.member.databinding.ItemForumCommentBinding
import org.jxxy.debug.member.fragment.ModifyDialog
import org.jxxy.debug.member.http.viewModel.MemberViewModel
import org.jxxy.debug.member.myListener.ModifyDialogListener
import org.jxxy.debug.member.viewHolder.ForumActivityViewHolder
import org.jxxy.debug.member.viewHolder.ForumCommentViewHolder

class ForumActivity:BaseActivity<ActivityForumBinding>() {
    val viewModel: MemberViewModel by lazy { ViewModelProvider(this).get(MemberViewModel::class.java) }
    override fun bindLayout(): ActivityForumBinding  = ActivityForumBinding.inflate(layoutInflater)
    val adapter = object : MultipleTypeAdapter() {
        override fun createViewHolder(
            viewType: Int,
            inflater: LayoutInflater,
            parent: ViewGroup
        ): RecyclerView.ViewHolder? = when(viewType){
            0-> ForumCommentViewHolder(ItemForumCommentBinding.inflate(inflater,parent,false))
            1 -> ForumActivityViewHolder(ItemForumActivityBinding.inflate(inflater,parent,false),supportFragmentManager)
            else -> null
        }
    }
    val page = 6
    val start = 1
    var entity : Forum? =null
    override fun initView() {
        entity = intent.getSerializableExtra("entity") as Forum?
        entity?.let {
            view.forumRV.adapter = adapter
            view.forumRV.addItemDecoration(CommonItemDecoration(8f))
            if (it.type==1)adapter.add(it)
        }
        view.editTextView.singleClick {
            loginCheck(this){
                val dialog = ModifyDialog()
                dialog.listener = object :ModifyDialogListener{
                    override fun submit(text:String, list: List<LocalMedia>) {
                        entity?.let { it1 -> viewModel.addForumComment(it1.commentId,text,list) }
                    }
                }
                dialog.show(supportFragmentManager,dialog.tag)
            }
        }
    }

    override fun subscribeUi() {
        viewModel.forumCommentLiceData.observe(this){
            it.onSuccess {
                if (entity?.type==0){
                   entity?.comments = it?.comment
                    entity?.flag = true
                    adapter.add(entity!!)
                }else {
                    it?.let {
                        it.comment?.let {
                            if (it.size >0){
                                if (adapter.itemCount == 1){
                                    adapter.add(it.map {
                                        it.apply {
                                            type = 0
                                        }
                                    })
                                }else{
                                    val last = it.last()?.apply {
                                        type = 0
                                    }
                                    last?.let { it1 ->
                                        adapter.add(it1) }
                                }
                            }
                        }

                    }
                }
            }
        }
        entity?.commentId?.let { viewModel.getForumComment(it,start,page) }
    }
}