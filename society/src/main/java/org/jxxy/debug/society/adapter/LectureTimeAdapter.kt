package org.jxxy.debug.society.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.society.bean.LectureBean
import org.jxxy.debug.society.bean.LectureTimeBean
import org.jxxy.debug.society.databinding.ItemLectureBinding
import org.jxxy.debug.society.databinding.ItemLecturetimeBinding
import org.jxxy.debug.society.holder.LectureHolder
import org.jxxy.debug.society.holder.LectureTimeHolder

class LectureTimeAdapter (list: ArrayList<LectureTimeBean>) : SingleTypeAdapter<LectureTimeBean>() {

    init {
        add(list)
    }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup,
    ): RecyclerView.ViewHolder =
        LectureTimeHolder  (ItemLecturetimeBinding.inflate(inflater))
}