package org.jxxy.debug.society.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.society.R
import org.jxxy.debug.society.bean.HistoryBean
import org.jxxy.debug.society.databinding.ItemHistory2Binding
import org.jxxy.debug.society.holder.HistoryHolder


class HistoryAtapter(list: ArrayList<HistoryBean>, private val tv: TextView,private val textTv: TextView,private val img: ImageView) : SingleTypeAdapter<HistoryBean>() {
     var thisPosition: Int = 0

    init {
        add(list)
    }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup,
    ): RecyclerView.ViewHolder =
        HistoryHolder(ItemHistory2Binding.inflate(inflater), tv,textTv,img, ::thisPosition)

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val historyHolder = holder as HistoryHolder

        historyHolder.view.yearTv.setBackgroundResource(
            if (position == thisPosition) R.drawable.elliptic_history_select
            else R.drawable.elliptic_history
        )
    }
}