package org.jxxy.debug.society.holder

import android.content.Intent
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.society.activity.AibreaklowActivity
import org.jxxy.debug.society.activity.StudylowActivity
import org.jxxy.debug.society.bean.StudylowBean
import org.jxxy.debug.society.databinding.ItemStudylowBinding

class StudeylowHolder(val binding: ItemStudylowBinding) : MultipleViewHolder<StudylowBean>(binding.root) {
    override fun setHolder(entity: StudylowBean) {
        binding.titleTv.text=entity.titleTv
        binding.imageImg.setImageResource(entity.imageImg)
        binding.imageImg.setOnClickListener(){

            val intent = Intent(context, StudylowActivity::class.java)
            context.startActivity(intent)
        }
    }


}