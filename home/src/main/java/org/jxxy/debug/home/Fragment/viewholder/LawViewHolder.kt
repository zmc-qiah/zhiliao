package org.jxxy.debug.home.Fragment.viewholder

import androidx.fragment.app.FragmentManager
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.dialog.LawDialog
import org.jxxy.debug.home.Fragment.item.Law
import org.jxxy.debug.home.databinding.ComponentLawBinding

class LawViewHolder(
    val binding: ComponentLawBinding,
    private val fragmentManager: FragmentManager
) :
    MultipleViewHolder<Law>(binding.root) {

    override fun setHolder(entity: Law) {
        binding.expertTitle.text = entity.typeName
        binding.lawProvenance.text = entity.lawInfo?.author
        val lawDialog = LawDialog()
        binding.lawEntry.text = entity.lawInfo?.title
        binding.lawText.text = entity.lawInfo?.content
        binding.root.singleClick {
            lawDialog.show(fragmentManager)
        }
    }

}