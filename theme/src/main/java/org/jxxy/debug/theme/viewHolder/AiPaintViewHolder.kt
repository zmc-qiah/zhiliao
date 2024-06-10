package org.jxxy.debug.theme.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dingmouren.layoutmanagergroup.skidright.SkidRightLayoutManager
import org.jxxy.debug.common.util.MoreViewAlpha1to0
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.theme.bean.ThemeAIPaint
import org.jxxy.debug.theme.bean.ThemeAIPaintBean
import org.jxxy.debug.theme.databinding.ItemAiPaintBinding
import org.jxxy.debug.theme.databinding.ItemPaintItemBinding

class AiPaintViewHolder(view:ItemAiPaintBinding):MultipleViewHolder2<ItemAiPaintBinding,ThemeAIPaint>(view) {
    val adapter = object :SingleTypeAdapter<ThemeAIPaintBean>(){
        override fun createViewHolder(
            viewType: Int,
            inflater: LayoutInflater,
            parent: ViewGroup
        ): RecyclerView.ViewHolder? = PaintViewHolder(
            ItemPaintItemBinding.inflate(inflater,parent,false)
        )
    }
    init {
        view.aiPaintRv.layoutManager = SkidRightLayoutManager(2f,0.8f)
        view.aiPaintRv.adapter = adapter
    }
    override fun setHolder(entity: ThemeAIPaint) {
        adapter.clearAndAdd(entity.list)
    }
    override fun setHolder(entity: ThemeAIPaint, payload: Any) {
        view.apply {
            val list = listOf(view.descriptionTV,view.aiPaintRv)
            if (payload as? Boolean ?: false){
                someViewAlpha0to1(list)
            }else{
                MoreViewAlpha1to0(listOf(list),200)
            }
        }
    }
}