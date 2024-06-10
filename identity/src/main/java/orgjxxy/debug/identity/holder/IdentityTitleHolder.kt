package orgjxxy.debug.identity.holder

import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.identity.databinding.ItemIdentityTitleBinding
import orgjxxy.debug.identity.bean.IdentityTitleBean

class IdentityTitleHolder(val binding: ItemIdentityTitleBinding) : MultipleViewHolder<IdentityTitleBean>(binding.root) {
    override fun setHolder(entity: IdentityTitleBean) {
        binding.tileTV.text=entity.text

        }
    }
