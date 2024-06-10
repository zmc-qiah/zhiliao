package org.jxxy.debug.society.holder

import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.society.bean.ExpfinshBean
import org.jxxy.debug.society.bean.ExptodayBean
import org.jxxy.debug.society.bean.MyclassBean
import org.jxxy.debug.society.databinding.ItemExpfinshBinding
import org.jxxy.debug.society.databinding.ItemExptodayBinding
import org.jxxy.debug.society.databinding.ItemMyclassBinding

class ExptodayHolder(val binding: ItemExptodayBinding ) : MultipleViewHolder<ExptodayBean>(binding.root) {
    override fun setHolder(entity: ExptodayBean) {
        binding.timeTv.text=entity.time
    }
}