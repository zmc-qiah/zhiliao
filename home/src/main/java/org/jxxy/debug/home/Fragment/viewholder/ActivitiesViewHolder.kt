package org.jxxy.debug.home.Fragment.viewholder

import android.util.Log
import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Activities
import org.jxxy.debug.home.databinding.PracticeComponentActivitiesBinding

class ActivitiesViewHolder(val binding: PracticeComponentActivitiesBinding) :
    MultipleViewHolder<Activities>(binding.root) {
    override fun setHolder(entity: Activities) {
        binding.activitiesTitle.text = entity.typeName
        binding.tv2.text = entity.activityInfo?.title ?: ""
        binding.imActivities.load(entity.activityInfo?.photo,6)
        binding.rc.singleClick {
            entity.activityInfo?.scheme?.navigation(context)
        }
    }
}