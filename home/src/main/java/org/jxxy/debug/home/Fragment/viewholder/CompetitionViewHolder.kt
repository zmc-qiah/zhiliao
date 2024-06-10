package org.jxxy.debug.home.Fragment.viewholder

import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Competition
import org.jxxy.debug.home.databinding.PracticeComponentCompetitionBinding

class CompetitionViewHolder (val binding: PracticeComponentCompetitionBinding) :
    MultipleViewHolder<Competition>(binding.root) {
    override fun setHolder(entity: Competition) {
        binding.competition.text = entity.typeName
        if(entity.activityInfos?.size?:0>3) {
            binding.title1.text = entity.activityInfos?.get(0)?.title
            binding.title2.text = entity.activityInfos?.get(1)?.title
            binding.title3.text = entity.activityInfos?.get(2)?.title
            binding.title4.text = entity.activityInfos?.get(3)?.title
            binding.competition1.load(entity.activityInfos?.get(0)?.photo,20)
            binding.competition2.load(entity.activityInfos?.get(1)?.photo,20)
            binding.competition3.load(entity.activityInfos?.get(2)?.photo,20)
            binding.competition4.load(entity.activityInfos?.get(3)?.photo,20)
            binding.competition1.singleClick {
                entity.activityInfos?.get(0)?.scheme?.navigation(context)
            }
            binding.competition2.singleClick {
                entity.activityInfos?.get(1)?.scheme?.navigation(context)
            }
            binding.competition3.singleClick {
                entity.activityInfos?.get(2)?.scheme?.navigation(context)
            }
            binding.competition4.singleClick {
                entity.activityInfos?.get(3)?.scheme?.navigation(context)
            }
        }
    }
}