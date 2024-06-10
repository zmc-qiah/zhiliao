package org.jxxy.debug.member.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.member.bean.MemberData
import org.jxxy.debug.member.databinding.ItemMember4partBinding
import org.jxxy.debug.member.databinding.ItemMemberDataBinding
import org.jxxy.debug.member.databinding.ItemMemberGroupBankBinding
import org.jxxy.debug.member.util.MyType

class MemberDataViewHolder(binding: ItemMemberDataBinding, private val onClick: (view: View) -> Unit) : MultipleViewHolder2<ItemMemberDataBinding, MemberData>(binding) {
    val adapter = object : MultipleTypeAdapter() {
        override fun createViewHolder(
            viewType: Int,
            inflater: LayoutInflater,
            parent: ViewGroup
        ): RecyclerView.ViewHolder? = when (viewType) {
            MyType.member4Part -> { Member4PartViewHolder(ItemMember4partBinding.inflate(inflater), onClick) }
            MyType.memberGroup -> { MemberGroupViewHolder(ItemMemberGroupBankBinding.inflate(inflater), onClick) }
            else -> { null }
        }
    }
    override fun setHolder(entity: MemberData) {
        view.memberDataRecyclerView.adapter = adapter
        view.memberDataRecyclerView.addItemDecoration(SpanItemDecoration(0f, 6f, 2))
        adapter.clear()
        adapter.add(entity.mebMember4Part)
        adapter.add(entity.group)
    }
}
