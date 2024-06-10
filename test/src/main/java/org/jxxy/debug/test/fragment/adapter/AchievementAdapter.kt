package org.jxxy.debug.test.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.test.databinding.ItemAchievementBinding
import org.jxxy.debug.test.fragment.bean.Achievement
import org.jxxy.debug.test.fragment.fragment.StatisticFragment

class AchievementAdapter : SingleTypeAdapter<Achievement>() {
    lateinit var fragmentManager : FragmentManager
    var nowChoose : Int = 1
    var way : (Int) -> Unit = {

    }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {
        return AchievementViewHolder(ItemAchievementBinding.inflate(inflater,parent,false))
    }

    inner class AchievementViewHolder(view : ItemAchievementBinding) : SingleViewHolder<ItemAchievementBinding,Achievement>(view){
        override fun setHolder(entity: Achievement) {
            if(entity.id == -1){
                view.root.hide()
            }else{
                view.root.show()
            }
            if(nowChoose == entity.id){
                view.isUseTv.show()
            }else{
                view.isUseTv.hide()
            }
            Glide.with(view.achievementItemIv)
                .load(entity.photoUrl)
                .into(view.achievementItemIv)
            view.achievementItemTv.text = entity.name
            view.root.singleClick {
                val statisticFragment = StatisticFragment(entity)
                statisticFragment.way={
                    way(entity.id)
                    nowChoose = entity.id
                    notifyItemRangeChanged(0,itemCount - 1)
                }
                statisticFragment.show(fragmentManager)
            }
        }
    }

    interface OnItemClick{
        fun onClick()
    }
}