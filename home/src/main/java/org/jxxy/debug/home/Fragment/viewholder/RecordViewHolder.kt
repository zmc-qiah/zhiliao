package org.jxxy.debug.home.Fragment.viewholder

import org.jxxy.debug.common.util.loads
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.home.Fragment.item.Record
import org.jxxy.debug.home.databinding.PracticeComponentRecordBinding

class RecordViewHolder (val binding: PracticeComponentRecordBinding) :
    MultipleViewHolder<Record>(binding.root) {
    override fun setHolder(entity: Record) {
        binding.recordTitle.text = entity.typeName
        binding.tv1.setText(entity.userInfo.userName)
        binding.iv1.loads(entity.userInfo.headPhoto,true,context)
        binding.number1.text = entity.num1.toString()
        binding.number3.text = entity.num2.toString()
        binding.number4.text = entity.num3.toString()
        binding.number5.text = entity.num4.toString()
    }
}