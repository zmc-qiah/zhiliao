package org.jxxy.debug.member.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.member.databinding.ItemForumInterfaceBinding
import org.jxxy.debug.member.databinding.ItemMemberDataBinding
import org.jxxy.debug.member.databinding.ItemMemberInfoBinding
import org.jxxy.debug.member.databinding.ItemMemberPointBinding
import org.jxxy.debug.member.databinding.ItemNewMemberDataBinding
import org.jxxy.debug.member.databinding.ItemPlanListBinding
import org.jxxy.debug.member.util.MyType
import org.jxxy.debug.member.viewHolder.MemberDataViewHolder
import org.jxxy.debug.member.viewHolder.MemberForumViewHolder
import org.jxxy.debug.member.viewHolder.MemberInfoViewHolder
import org.jxxy.debug.member.viewHolder.MemberPlanViewHolder
import org.jxxy.debug.member.viewHolder.MemberPointViewHolder
import org.jxxy.debug.member.viewHolder.NewMemberDataViewHolder

class MemberAdapter(val activity: FragmentActivity, private val onClick: (view: View) -> Unit) : MultipleTypeAdapter() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? = when (viewType) {
        MyType.MemberInfo -> {
            MemberInfoViewHolder(ItemMemberInfoBinding.inflate(inflater,parent,false), onClick)
        }
        MyType.MemberPoint -> {
            MemberPointViewHolder(ItemMemberPointBinding.inflate(inflater,parent,false), onClick)
        }
        MyType.MemberData -> {
            NewMemberDataViewHolder(ItemNewMemberDataBinding.inflate(inflater,parent,false), onClick)
        }
        MyType.MemberPlan -> {
            MemberPlanViewHolder(ItemPlanListBinding.inflate(inflater,parent,false), onClick)
        }
        MyType.MemberForum -> {
            MemberForumViewHolder(ItemForumInterfaceBinding.inflate(inflater,parent,false), activity, onClick)
        }
        else -> null
    }
}
