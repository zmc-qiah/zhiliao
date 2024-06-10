package org.jxxy.debug.test.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.test.databinding.ItemTheChartsContentBinding
import org.jxxy.debug.test.databinding.ItemTheChartsMemberBinding
import org.jxxy.debug.test.fragment.bean.TheChartsMember

class TheChartsAdapter : MultipleTypeAdapter() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {
        return when(viewType){
            1 -> ItemTheChartsMemberViewHolder(ItemTheChartsMemberBinding.inflate(inflater,parent,false))
            else -> null
        }
    }

    inner class ItemTheChartsMemberViewHolder(view: ItemTheChartsMemberBinding) : MultipleViewHolder2<ItemTheChartsMemberBinding,TheChartsMember>(view){
        override fun setHolder(entity: TheChartsMember) {
            view.theChartsItemIv.load(entity.url,true)
            view.theChartsItemDamageTv.text = entity.Damage
            view.theChartsItemNameTv.text = entity.name
            view.theChartsItemTv.text = entity.member.toString()
        }
    }
    inner class ItemTheChartsContentViewHolder(view : ItemTheChartsContentBinding) : MultipleViewHolder2<ItemTheChartsContentBinding,TheChartsMember>(view){
        override fun setHolder(entity: TheChartsMember) {

        }
    }
}