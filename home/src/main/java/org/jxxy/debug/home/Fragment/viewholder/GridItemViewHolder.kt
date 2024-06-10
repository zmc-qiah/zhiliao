package org.jxxy.debug.home.Fragment.viewholder

import android.util.Log
import navigation
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Grid
import org.jxxy.debug.home.Fragment.item.GridFour
import org.jxxy.debug.home.databinding.ItemGridBinding
import org.jxxy.debug.home.databinding.ItemNewsBinding

class GridItemViewHolder (view: ItemGridBinding) :
    SingleViewHolder<ItemGridBinding, Grid>(view) {
    override fun setHolder(entity: Grid) {
        val List = entity.grid4Infos
        if (List != null && position < List.size) {
            val tool = List[position]
            view.grid1.load(tool.photo)
            view.gridTv1.text = tool.title
            Log.d("GridViewHolder", "setHolder:${List}")
            view.root.singleClick {
                tool?.scheme?.navigation(context)
            }
        }
    }
}