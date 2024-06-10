package org.jxxy.debug.society.holder
import android.os.Bundle
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.society.activity.DiscussAnswerActivity
import org.jxxy.debug.society.bean.Select
import org.jxxy.debug.society.databinding.ItemSelectBinding
class SelectHolder (view : ItemSelectBinding):
    SingleViewHolder<ItemSelectBinding, Select>(view){
    override fun setHolder(entity: Select) {
        val bundle=Bundle()
        view.titleTv.text = entity.title
        view.textTv.text=entity.text
        view.tagTv.text=entity.tag
        itemView.singleClick {
            bundle.putInt("answerid", entity.answerid)
            context?.startActivity<DiscussAnswerActivity>(bundle)
        }

    }
}