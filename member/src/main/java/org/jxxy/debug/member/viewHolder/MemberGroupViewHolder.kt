package org.jxxy.debug.member.viewHolder

import android.graphics.Color
import android.view.View
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.member.R
import org.jxxy.debug.member.bean.MemberGroup
import org.jxxy.debug.member.databinding.ItemMemberGroupBankBinding

class MemberGroupViewHolder(binding: ItemMemberGroupBankBinding, private val onClick: (view: View) -> Unit) : MultipleViewHolder2<ItemMemberGroupBankBinding, MemberGroup>(binding) {
    override fun setHolder(entity: MemberGroup) {
        view.groupBar.singleClick {
            onClick(it)
        }
        entity.groupRankList?.let {
            var nameList = ArrayList<String>()
            var bars = ArrayList<BarEntry>()
            when (it.size) {
                1 -> {
                    nameList.add(it[0].userName ?: "未知用户")
                    bars.add(BarEntry(0f, it[0].score.toFloat()))
                }
                2 -> {
                    nameList.add(it[1].userName ?: "未知用户")
                    bars.add(BarEntry(0f, it[1].score.toFloat()))
                    nameList.add(it[0].userName ?: "未知用户")
                    bars.add(BarEntry(1f, it[0].score.toFloat()))
                }
                3 -> {
                    nameList.add(it[1].userName ?: "未知用户")
                    bars.add(BarEntry(0f, it[1].score.toFloat()))
                    nameList.add(it[0].userName ?: "未知用户")
                    bars.add(BarEntry(1f, it[0].score.toFloat()))
                    nameList.add(it[2].userName ?: "未知用户")
                    bars.add(BarEntry(2f, it[2].score.toFloat()))
                }
                else -> {}
            }
            if (nameList.size != 0) {
                initBar(nameList, bars)
                view.groupNameAndNumTV.text = "${entity.groupName}"
//                view.groupSumTV.text = "群组人数${entity.peopleNums?.toString()}"
            }
        }
    }
    private fun initBar(list: List<String>, bars: List<BarEntry>) {
        val barChart = view.groupBar
        barChart.apply {
            val entries = bars
            val barDataSet = BarDataSet(entries, "Bar Data")
            barDataSet.setColors(
                listOf(
                    ResourceUtil.getColor(R.color.point_week_change_end),
                    ResourceUtil.getColor(R.color.color1),
                    ResourceUtil.getColor(R.color.point_week_change_begin)
                )
            )
            isScaleXEnabled = false
            isScaleYEnabled = false
            barDataSet.valueTextColor = Color.BLACK
            barDataSet.valueTextSize = 12f
            val barData = BarData(barDataSet)
            data = barData
            barData.barWidth = 0.5f
            setDrawBarShadow(false)
            setDrawValueAboveBar(true)
            description.isEnabled = false
            legend.isEnabled = false
            animateY(1000, Easing.Linear)
            animateX(1000, Easing.Linear)
            val xAxis: XAxis = xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.granularity = 1f
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase?):
                    String = list.get(value.toInt())
            }
            axisLeft.isEnabled = false
            axisLeft.setAxisMinimum(0f)
            axisRight.isEnabled = false
            description.text = "群组内积分排行，点击查看更多"
            invalidate()
        }
    }
}
