package org.jxxy.debug.society.holder

import android.content.Intent
import androidx.appcompat.view.menu.MenuView.ItemView
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.society.R
import org.jxxy.debug.society.activity.AibreaklowActivity
import org.jxxy.debug.society.bean.AibreaklowBean
import org.jxxy.debug.society.bean.AnswerBean
import org.jxxy.debug.society.databinding.ItemAibreaklowBinding
import org.jxxy.debug.society.databinding.ItemAnswerAnswerBinding

class AibreaklowHolder (val binding: ItemAibreaklowBinding) : MultipleViewHolder<AibreaklowBean>(binding.root) {
    override fun setHolder(entity: AibreaklowBean) {
        binding.titleTv.text=entity.titleTv
        binding.imageImg.setImageResource(entity.imageId)
        binding.imageImg.setOnClickListener(){

            val intent = Intent(context, AibreaklowActivity::class.java)
            context.startActivity(intent)
        }
        }


}