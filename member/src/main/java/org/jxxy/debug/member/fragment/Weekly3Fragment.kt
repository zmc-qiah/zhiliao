package org.jxxy.debug.member.fragment

import android.animation.ObjectAnimator
import android.graphics.Color
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.coroutines.* // ktlint-disable no-wildcard-imports
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.member.R
import org.jxxy.debug.member.databinding.FragmentWeekly3Binding
import org.jxxy.debug.member.http.respone.WeekReportResponce

class Weekly3Fragment(val info: WeekReportResponce) : BaseFragment<FragmentWeekly3Binding>() {
    val list:MutableList<String> by lazy {
        mutableListOf<String>()
    }
    override fun bindLayout(): FragmentWeekly3Binding = FragmentWeekly3Binding.inflate(layoutInflater)

    override fun initView() {
        val barChart = find.barChart
        val entries = mutableListOf<BarEntry>()
        var a = 0f
        info.weekStudyInfos!!.forEach {
            list.add(it.studyName!!)
            entries.add(  BarEntry(a++, it.studyTime!!.substring(0, it.studyTime!!.length-1).toFloat()),)
        }
        barChart.apply {
            val barDataSet = BarDataSet(entries, "")
            barDataSet.setColors(
                listOf(
                    ResourceUtil.getColor(R.color.point_week_change_begin),
                    ResourceUtil.getColor(R.color.point_week_change_end),
                    ResourceUtil.getColor(R.color.bar3),
                    ResourceUtil.getColor(R.color.bar4)
                )
            )
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
            description.isEnabled = false
            invalidate()
        }
        CoroutineScope(Dispatchers.Default).launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val fadeInAnimator = ObjectAnimator.ofFloat(find.weekly3Text, "alpha", 0f, 1f)
                fadeInAnimator.duration = 3000 // 设置动画持续时间，单位为毫秒
                fadeInAnimator.start()
            }
        }
    }


    override fun subscribeUi() {
    }
}
