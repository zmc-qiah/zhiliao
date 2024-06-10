package org.jxxy.debug.home.Fragment.viewholder

import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.home.Fragment.item.Theme
import org.jxxy.debug.home.databinding.PracticeComponentThemeBinding

class ThemeViewHolder (val binding: PracticeComponentThemeBinding) :
    MultipleViewHolder<Theme>(binding.root) {
    override fun setHolder(entity: Theme) {
        binding.competition.text = entity.typeName
        binding.theme.setText("主题贡献者：美猴王")
        binding.tv.text = entity.topicTitle
    }
}