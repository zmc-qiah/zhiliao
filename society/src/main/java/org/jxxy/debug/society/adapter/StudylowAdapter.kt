package org.jxxy.debug.society.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.society.bean.StudyBean
import org.jxxy.debug.society.databinding.ItemStudyBinding
import org.jxxy.debug.society.holder.StudyHolder

class StudylowAdapter (list: ArrayList<StudyBean >): SingleTypeAdapter<StudyBean>() {
    init {
        add(list)
    }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder= StudyHolder (ItemStudyBinding.inflate(inflater))
}