package orgjxxy.debug.identity.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.identity.R
import org.jxxy.debug.identity.databinding.ItemIdentityTextBinding
import org.jxxy.debug.identity.databinding.ItemIdentityTitleBinding
import orgjxxy.debug.identity.holder.IdentityContextHolder
import orgjxxy.debug.identity.holder.IdentityTitleHolder

class IdentityAdapter(val list: ArrayList<Int>, val num: Int) : MultipleTypeAdapter() {
    var thisPosition: Int = list[num]
    var currentQuestion: Int = num
    var isOptionClick: Boolean = true
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {

        return when (viewType) {
            0 -> IdentityTitleHolder(ItemIdentityTitleBinding.inflate(inflater))
            1 -> IdentityContextHolder(ItemIdentityTextBinding.inflate(inflater), ::thisPosition, ::isOptionClick)
            else -> null
        }
    }
    //实现记忆选项和选中状态
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val IdentityContextHolder = holder as IdentityContextHolder
        var currentPosition = 0
        currentPosition = if (isOptionClick) {
            thisPosition
        } else {
            list[currentQuestion]
        }

        if (position == currentPosition) {
            IdentityContextHolder.binding.textLinear.setBackgroundResource(R.drawable.rounded_whitewithblue)
            IdentityContextHolder.binding.correctIcon.visibility=View.VISIBLE
            Log.i("23333333","position: $position###currentPosition: $currentPosition")
            Log.i("23333333","$isOptionClick")
            if (isOptionClick) {
                list[currentQuestion] = thisPosition
            }
        } else {
            IdentityContextHolder.binding.textLinear.setBackgroundResource(R.drawable.rounded_whitewithgrey)
            IdentityContextHolder.binding.correctIcon.visibility=View.GONE
        }

    }

}