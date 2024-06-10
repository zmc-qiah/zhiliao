package org.jxxy.debug.member.viewHolder

import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.member.R
import org.jxxy.debug.member.activity.PointsDetailsActivity
import org.jxxy.debug.member.bean.MemberPoint
import org.jxxy.debug.member.databinding.ItemMemberPointBinding
import java.util.regex.Pattern

class MemberPointViewHolder(binding: ItemMemberPointBinding, private val onClick: (view: View) -> Unit) : MultipleViewHolder2<ItemMemberPointBinding, MemberPoint>(binding) {
    val lineList = ArrayList<Entry>()
    val lineLabelList = ArrayList<String>()
    override fun setHolder(entity: MemberPoint) {
        entity.apply {
//            view.pointView.singleClick {
//                onClick(it)
//            }
//            view.userSumPointTV.text = memberScore?.sumsScore.toString()
//            view.userTodayPointTV.text = memberScore?.todayScore.toString()
//            view.userDaysTV.text = togetherDay
            memberScore?.day7Score?.let {
                val yearList: MutableList<String> = ArrayList()
                val monthList: MutableList<String> = ArrayList()
                val dayList: MutableList<String> = ArrayList()
                val pattern = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})")
                var a = 0f
                lineLabelList.clear()
                lineList.clear()
                it.forEach {
                    lineList.add(Entry(a++, it.sums.toFloat()))
                    val matcher = pattern.matcher(it.data)
                    if (matcher.find()) {
                        yearList.add(matcher.group(1)!!)
                        monthList.add(matcher.group(2)!!)
                        dayList.add(matcher.group(3)!!)
                    }
                }
                if (yearList.distinct().size == 1) {
                    if (monthList.distinct().size == 1) {
                        for (i in 0..it.size - 1) {
                            lineLabelList.add(dayList.get(i))
                        }
                    } else {
                        for (i in 0..it.size - 1) {
                            lineLabelList.add(monthList.get(i) + "." + dayList.get(i))
                        }
                    }
                } else {
                    for (i in 0..it.size - 1) {
                        lineLabelList.add(yearList.get(i) + "." + monthList.get(i) + "." + dayList.get(i))
                    }
                }
                if (!lineList.isEmpty()){
                    initLine(lineLabelList, lineList)
                }
            }
        }
    }
    fun initLine(list: List<String>, entryList: List<Entry>) {
        val tally = view.userLineChart
        Log.d("corekit", "initLine: "+entryList)
        tally.apply {
            val xAxis: XAxis = xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.labelCount = list.size
            xAxis.granularity = 1f
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    Log.d("corekit", "initLine: "+(value).toInt())
                    return list.getOrNull((value).toInt()) ?:""
                }
            }
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            description.isEnabled = false
            singleClick {
                context.startActivity(PointsDetailsActivity::class.java)
            }
            isDragEnabled = false
            isScaleXEnabled = false
            isScaleYEnabled = false
            isHighlightPerTapEnabled = false
        }
        val lineDataSet = LineDataSet(entryList, null)
        lineDataSet.apply {
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            color = ResourceUtil.getColor(R.color.line_line)
            setCircleColor(ResourceUtil.getColor(R.color.line_point))
            lineWidth = 2f
            circleRadius = 4f
            setDrawCircleHole(false)
            valueTextSize = 12f
            valueTextColor = ResourceUtil.getColor(org.jxxy.debug.corekit.R.color.black)
        }
        val LineData = LineData(lineDataSet)
        tally.data = LineData
        tally.invalidate()
    }
}
