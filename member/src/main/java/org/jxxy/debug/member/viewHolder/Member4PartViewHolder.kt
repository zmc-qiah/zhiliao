package org.jxxy.debug.member.viewHolder

import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.member.R
import org.jxxy.debug.member.activity.ResourcesActiviy
import org.jxxy.debug.member.bean.Member4Part
import org.jxxy.debug.member.databinding.ItemMember4partBinding

class Member4PartViewHolder(binding: ItemMember4partBinding, private val onClick: (view: View) -> Unit) : MultipleViewHolder2<ItemMember4partBinding, Member4Part>(binding) {
    val pieList = ArrayList<PieEntry>()
    override fun setHolder(entity: Member4Part) {
        view.userWeeklyButton.singleClick {
            onClick(it)
        }
        entity?.let {
            pieList.clear()
            it.followersNum.let {
                if (it.toInt() != 0) {
                    pieList.add(PieEntry(1f, "订阅"))
                }
            }
            it.collectionsNum.let {
                if (it.toInt() != 0) {
                    pieList.add(PieEntry(1f, "收藏"))
                }
            }
            it.notesNum.let {
                if (it.toInt() != 0) {
                    pieList.add(PieEntry(1f, "笔记"))
                }
            }
            it.historiesNum.let {
                if (it.toInt() != 0) {
                    pieList.add(PieEntry(1f, "今日历史"))
                }
            }
            initPie(pieList)
        }
    }
    private fun initPie(list: List<PieEntry>) {
        view.userRescourcesPieC.apply {
            dragDecelerationFrictionCoef = 0.5f
            rotationAngle = 315f
            isRotationEnabled = false
            isHighlightPerTapEnabled = true
            animateXY(1000, 1000)
            val pieDataSet = PieDataSet(list, "")
            pieDataSet.apply {
                val customColors = mutableListOf<Int>(
                    ResourceUtil.getColor(R.color.point_week_change_begin),
                    ResourceUtil.getColor(R.color.point_week_change_end),
                    ResourceUtil.getColor(R.color.bar4),
                    ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_200)
                )
                this.colors = customColors
                sliceSpace = 2f
                selectionShift = 8f
                valueTextSize = 0f
                description = null
                legend.isEnabled = false
            }
            data = PieData(pieDataSet)
            setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    e as PieEntry
                    val intent = Intent(context, ResourcesActiviy::class.java)
                    intent.putExtra("page", e.label)
                    context.startActivity(intent)
                }
                override fun onNothingSelected() {
                }
            })
            invalidate()
        }
    }
}
