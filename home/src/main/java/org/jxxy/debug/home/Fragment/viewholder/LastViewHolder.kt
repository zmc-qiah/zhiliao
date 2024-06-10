package org.jxxy.debug.home.Fragment.viewholder

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import navigation
import org.jxxy.debug.corekit.common.CommonServiceManager
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Lastview
import org.jxxy.debug.home.Fragment.item.Like
import org.jxxy.debug.home.R
import org.jxxy.debug.home.databinding.ComponentLastViewBinding


class LastViewHolder(val binding: ComponentLastViewBinding) :
    MultipleViewHolder<Lastview>(binding.root) {

    override fun setHolder(entity: Lastview) {
        binding.tvLastText.text=entity.lastBrowseInfo?.resourceTitle
        binding.tvLastText.singleClick {
            entity.lastBrowseInfo?.scheme?.navigation(context)
        }
        //TODO 二级首页路由跳转
 /*       binding.con3.singleClick {
            val intent = Intent(binding.root.context, RecommendActivity::class.java)
            binding.root.context.startActivity(intent)
        }
        binding.con4.singleClick {
            val intent = Intent(binding.root.context, HistoryActivity::class.java)
            binding.root.context.startActivity(intent)
        }
        binding.con5.singleClick {
            val intent = Intent(binding.root.context, PracticeActivity::class.java)
            binding.root.context.startActivity(intent)
        }
        binding.con6.singleClick {
            val intent = Intent(binding.root.context, DiscussActivity::class.java)
            binding.root.context.startActivity(intent)
        }*/

    }

}