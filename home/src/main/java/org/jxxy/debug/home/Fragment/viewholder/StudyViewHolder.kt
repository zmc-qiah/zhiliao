package org.jxxy.debug.home.Fragment.viewholder

import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Study_2
import org.jxxy.debug.home.databinding.StudyBinding

class StudyViewHolder (val binding: StudyBinding) :
    MultipleViewHolder<Study_2>(binding.root) {
    override fun setHolder(entity: Study_2) {
        binding.root.singleClick {
            entity.scheme?.navigation(context)
        }
    }
}