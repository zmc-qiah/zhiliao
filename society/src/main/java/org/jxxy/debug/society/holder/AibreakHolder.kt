package org.jxxy.debug.society.holder
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.society.bean.AibreakBean
import org.jxxy.debug.society.databinding.ItemAibreakBinding
class AibreakHolder (view :  ItemAibreakBinding):
    SingleViewHolder<ItemAibreakBinding, AibreakBean>(view){
    override fun setHolder(entity: AibreakBean) {
        view.textTv.text=entity.textTv
        view.titleTv.text=entity.titleTv

        }
    }

