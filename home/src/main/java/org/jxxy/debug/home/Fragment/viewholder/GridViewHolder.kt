package org.jxxy.debug.home.Fragment.viewholder

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Grid
import org.jxxy.debug.home.Fragment.item.bean.GridBean
import org.jxxy.debug.home.databinding.ComponentGridBinding
import org.jxxy.debug.home.databinding.ItemGridBinding

class GridViewHolder (val binding: ComponentGridBinding) :
    MultipleViewHolder<Grid>(binding.root) {
//    private val adapter = GridAdapter()
    private val adapter = object : SingleTypeAdapter<GridBean>() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? = object: SingleViewHolder<ItemGridBinding, GridBean>(ItemGridBinding.inflate(inflater, parent,false)) {
        override fun setHolder(entity: GridBean) {
            view.grid1.load(entity.photo)
            view.gridTv1.text = entity.title
            Log.d("GridViewHolder", "setHolder:${entity}")
            view.root.singleClick {
                entity?.scheme?.navigation(context)
            }
        }
    }
}

    init {
        binding.recyclerview.layoutManager = GridLayoutManager(itemView.context, 2)
        binding.recyclerview.addItemDecoration(SpanItemDecoration(8f, 8f, 2))
        binding.recyclerview.adapter = adapter
    }
    override fun setHolder(entity: Grid) {
        entity.grid4Infos?.let { List ->
            adapter.clear()
            adapter.add(List)
//            val tools = Grid(entity.type,entity.typeName)
//            for (i in 1..80) {
//            }
        }
    }
}