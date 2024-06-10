package org.jxxy.debug.society.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.society.databinding.ItemClassBinding
import org.jxxy.debug.society.databinding.ItemExpfinshBinding
import org.jxxy.debug.society.databinding.ItemExptodayBinding
import org.jxxy.debug.society.databinding.ItemMyclassBinding
import org.jxxy.debug.society.databinding.ItemRecommendclassBinding
import org.jxxy.debug.society.holder.ExcellentclassHolder
import org.jxxy.debug.society.holder.ExpfinshHolder
import org.jxxy.debug.society.holder.ExptodayHolder
import org.jxxy.debug.society.holder.MyclassHolder
import org.jxxy.debug.society.holder.RecommendclassHolder

class ExperienceAdapter: MultipleTypeAdapter() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {

        return when (viewType) {

            0 ->  ExptodayHolder (ItemExptodayBinding.inflate(inflater))
            1 ->  ExpfinshHolder (
                ItemExpfinshBinding.inflate(
                    inflater
                )
            )
            else -> null
        }
    }


}