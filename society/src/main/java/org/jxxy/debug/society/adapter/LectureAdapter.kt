package org.jxxy.debug.society.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.society.bean.HotlistBean
import org.jxxy.debug.society.bean.LectureBean
import org.jxxy.debug.society.databinding.ItemHotlistBinding
import org.jxxy.debug.society.databinding.ItemLectureBinding
import org.jxxy.debug.society.holder.HotlistHolder
import org.jxxy.debug.society.holder.LectureHolder

class LectureAdapter (list: ArrayList<LectureBean>) : SingleTypeAdapter<LectureBean>() {

    init {
        add(list)
    }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup,
    ): RecyclerView.ViewHolder =
        LectureHolder  (ItemLectureBinding.inflate(inflater))
}