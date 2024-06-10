package org.jxxy.debug.society.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.society.bean.PlayBean
import org.jxxy.debug.society.databinding.ItemPlayBinding
import org.jxxy.debug.society.holder.PlayHolder

class PlayAdapter(list: ArrayList<PlayBean>) : SingleTypeAdapter<PlayBean>() {
    init {
        add(list)
    }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup,
    ): RecyclerView.ViewHolder =
        PlayHolder  (ItemPlayBinding.inflate(inflater))
}