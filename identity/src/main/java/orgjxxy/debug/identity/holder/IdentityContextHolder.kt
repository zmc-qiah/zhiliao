package orgjxxy.debug.identity.holder

import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.identity.databinding.ItemIdentityTextBinding
import orgjxxy.debug.identity.bean.IdentityContextBean
import kotlin.reflect.KMutableProperty0

class IdentityContextHolder(val binding: ItemIdentityTextBinding,private var thisPosition: KMutableProperty0<Int>
    ,private var isOptionClick: KMutableProperty0<Boolean>) : MultipleViewHolder<IdentityContextBean>(binding.root) {
    override fun setHolder(entity: IdentityContextBean) {
       binding.optionTv.text=entity.opention
        binding.contentTv.text=entity.context
        itemView.setOnClickListener {
            //设置选中的位置
            thisPosition.set(layoutPosition)
            isOptionClick.set(true)
            bindingAdapter?.notifyDataSetChanged()

        }
    }
}