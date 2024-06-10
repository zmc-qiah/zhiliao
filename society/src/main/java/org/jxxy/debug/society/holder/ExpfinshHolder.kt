package org.jxxy.debug.society.holder

import android.widget.ImageView
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.society.R
import org.jxxy.debug.society.bean.ExpfinshBean
import org.jxxy.debug.society.bean.MyclassBean
import org.jxxy.debug.society.databinding.ItemExpfinshBinding
import org.jxxy.debug.society.databinding.ItemMyclassBinding
import org.jxxy.debug.society.dialog.SocialImageDialog

class ExpfinshHolder(val binding: ItemExpfinshBinding) : MultipleViewHolder<ExpfinshBean>(binding.root) {
    override fun setHolder(entity: ExpfinshBean) {
      /*  val dailyDialog = SocialImageDialog(entity.image as ImageView)*/
       binding.textTv.text=entity.text
        binding.expImg.load(entity.image,10)
        binding.timeTv2.text=entity.time
        binding.authorhead.load(entity.authorhead,200)
        binding.nameTv.text=entity.nameTv
        binding.yearTv .text=entity.yearTv
        binding.monthTv .text=entity.monthTv
        binding.dayTv .text=entity.dayTv
        binding.expImg.singleClick {
            /*dailyDialog.show()*/
        }
    }
}