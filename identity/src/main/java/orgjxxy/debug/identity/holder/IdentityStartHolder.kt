package orgjxxy.debug.identity.holder

import org.jxxy.debug.common.util.IdentityUtil
import org.jxxy.debug.corekit.http.AddressManager
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.identity.databinding.ItemIdentitystartBinding
import orgjxxy.debug.identity.MyClickListener
import orgjxxy.debug.identity.bean.IdentityChooseBean
import orgjxxy.debug.identity.http.viewModel.IdentityViewModel
import kotlin.reflect.KFunction0

class IdentityStartHolder (val binding: ItemIdentitystartBinding, val viewModel: IdentityViewModel,val finish: KFunction0<Unit>,val listener: MyClickListener?
) : MultipleViewHolder<IdentityChooseBean>(binding.root) {
    private val Optionlist = ArrayList<Char>().apply {
        repeat(9) {
            add('A')
        }
    }
    override fun setHolder(entity: IdentityChooseBean) {
        binding.DeafaultImg.load(entity.img)
        binding.describeTv.text=entity.context
        itemView.singleClick {
            AddressManager.updateCity(entity.identity.toString())
            listener?.onClick(entity.identity,Optionlist)
        }



    }
}