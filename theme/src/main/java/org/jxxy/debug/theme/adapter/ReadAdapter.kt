package org.jxxy.debug.theme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.common.service.WebService
import org.jxxy.debug.corekit.common.CommonServiceManager
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.theme.bean.ReadSomethingInnerResult
import org.jxxy.debug.theme.databinding.ItemAiReadBinding
import org.jxxy.debug.theme.databinding.ItemOcrBinding

class ReadAdapter : MultipleTypeAdapter() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {
        return when(viewType){
            1 -> AiReadViewHolder(ItemAiReadBinding.inflate(inflater,parent,false))
            2 -> AiOcrViewHolder(ItemOcrBinding.inflate(inflater,parent,false))
            else -> null
        }
    }

    inner class AiReadViewHolder(view : ItemAiReadBinding) : MultipleViewHolder2<ItemAiReadBinding,ReadSomethingInnerResult>(view){
        override fun setHolder(entity: ReadSomethingInnerResult) {
            if(entity.baike?.baiKeUrl != null){
                view.checkBtn.show()
            }else{
                view.checkBtn.hide()
            }
            view.checkBtn.singleClick {
                if(entity.baike?.baiKeUrl != null){
                    CommonServiceManager.service<WebService>()?.gotoWebH5(context, entity.baike?.baiKeUrl)
                }
            }
            view.nameTv.text = entity.keyword
            view.scoreTv.text = "相似度:" + entity.score
        }
    }

    inner class AiOcrViewHolder(view : ItemOcrBinding) : MultipleViewHolder2<ItemOcrBinding,ReadSomethingInnerResult>(view){
        override fun setHolder(entity: ReadSomethingInnerResult) {
            view.ocrTv.text = entity.keyword
        }
    }
}