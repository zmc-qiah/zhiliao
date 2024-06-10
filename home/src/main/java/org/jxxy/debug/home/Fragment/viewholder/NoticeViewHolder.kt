package org.jxxy.debug.home.Fragment.viewholder

import android.content.Intent
import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Notice
import org.jxxy.debug.home.databinding.ComponentNewsBinding
import org.jxxy.debug.home.databinding.PracticeComponentNoticeBinding

class NoticeViewHolder (val binding: PracticeComponentNoticeBinding) :
    MultipleViewHolder<Notice>(binding.root) {
    override fun setHolder(entity: Notice) {
//        binding.noticeText.gone()
        binding.noticeText.text = entity.advertise
        binding.noticeText.isSelected = true
        binding.root.singleClick {
            entity.scheme1?.navigation(context)
        }
        //TODO 二级首页路由跳转
      /*  binding.im1.singleClick {
            val intent = Intent(binding.root.context, LectureActivity::class.java)
            binding.root.context.startActivity(intent)
        }
        binding.im2.singleClick {
            val intent = Intent(binding.root.context, PlayActivity::class.java)
            binding.root.context.startActivity(intent)
        }
        binding.im3.singleClick {
            val intent = Intent(binding.root.context, ExperienceActivity::class.java)
            binding.root.context.startActivity(intent)
        }*/
    }

}