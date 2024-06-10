package org.jxxy.debug.test.fragment.activity

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.test.databinding.ActivityStatisticBinding
import org.jxxy.debug.test.fragment.viewModel.QuestionViewModel
import kotlin.collections.ArrayList

class StatisticActivity : BaseActivity<ActivityStatisticBinding>() {

    val viewModel:QuestionViewModel by lazy {
        ViewModelProvider(this).get(QuestionViewModel::class.java)
    }
    val list : ArrayList<BarEntry> by lazy{
        ArrayList()
    }
    val dateList : ArrayList<String> by lazy {
        ArrayList()
    }
    override fun bindLayout(): ActivityStatisticBinding {
        return ActivityStatisticBinding.inflate(layoutInflater)
    }

    override fun initView() {
//        dateList.addAll(listOf("12","3","6","7","9","14","4"))
//        val values = listOf(10f,15f,20f,25f,30f,35f,40f)
//        list.add(BarEntry(1f, floatArrayOf(20f,10f)))
//        list.add(BarEntry(2f, floatArrayOf(30f,10f)))
//        list.add(BarEntry(3f, floatArrayOf(10f,5f)))
//        list.add(BarEntry(4f, floatArrayOf(15f,5f)))
//        list.add(BarEntry(5f, floatArrayOf(20f,7f)))
//        list.add(BarEntry(6f, floatArrayOf(28f,13f)))
//        list.add(BarEntry(7f, floatArrayOf(15f,3f)))


    }

    override fun subscribeUi() {
        viewModel.getStatisticsLiveData.observe(this){
            it.onSuccess {
                it?.let {
                    for (i in 1..7) {
                        list.add(BarEntry(i.toFloat(), floatArrayOf(it.days7[i - 1].rights.toFloat(),it.days7[i - 1].wrongs.toFloat())))
                        dateList.add(it.days7[i - 1].date)
                    }
                    view.answerSumTv.setTextColor(Color.parseColor(
                        when  {
                            it.answerNums >= 40 -> "#00CC65"
                            it.answerNums >= 20 -> "#FFA500"
                            it.answerNums >= 0 -> "#FC656A"
                            else -> "#FF4500"
                        }
                    ))
                    view.answerSumTv.text = it.answerNums.toString()
                    view.daysTv.setTextColor(Color.parseColor(
                        when  {
                            it.studyDays >= 40 -> "#00CC65"
                            it.studyDays >= 20 -> "#FFA500"
                            it.studyDays >= 0 -> "#FC656A"
                            else -> "#FF4500"
                        }
                    ))
                    view.daysTv.text = it.studyDays.toString()
                    view.percentTv.setTextColor(Color.parseColor(
                        when  {
                            it.right.substring(0,it.right.lastIndex).toInt() >= 80 -> "#00CC65"
                            it.right.substring(0,it.right.lastIndex).toInt() >= 60 -> "#FFA500"
                            it.right.substring(0,it.right.lastIndex).toInt() >= 0 -> "#FC656A"
                            else -> "#FF4500"
                        }
                    ))
                    view.percentTv.text = it.right.substring(0,it.right.lastIndex)
                    val dataSet = BarDataSet(list,"")
                    dataSet.colors = listOf(Color.parseColor("#1EABFA"),Color.parseColor("#FC656A"))
                    dataSet.barBorderColor = Color.BLACK
                    dataSet.valueFormatter = object :ValueFormatter(){
                        override fun getFormattedValue(value: Float): String {
                            if(value.toInt() == 0)
                                return ""
                            return value.toInt().toString()
                        }
                    }
                    val lend1 = LegendEntry()
                    lend1.formColor = Color.parseColor("#1EABFA")
                    lend1.label = "正确"
                    val lend2 = LegendEntry()
                    lend2.formColor = Color.parseColor("#FC656A")
                    lend2.label = "错误"
                    view.statisticCt.legend.setCustom(listOf(lend1,lend2))
                    view.statisticCt.legend.textSize = 12f
                    view.statisticCt.xAxis.run {
                        valueFormatter = object : ValueFormatter(){
                            override fun getFormattedValue(value: Float): String {
                                return dateList.getOrNull(value.toInt() - 1) ?: ""
                            }
                        }
                        setDrawGridLines(false)
                        position = XAxis.XAxisPosition.BOTTOM_INSIDE
                    }
                    view.statisticCt.axisRight.isEnabled = false
                    view.statisticCt.data = BarData(dataSet)
                    view.statisticCt.axisLeft.setDrawGridLines(false)
                    view.statisticCt.axisRight.setDrawGridLines(false)
                    view.statisticCt.description.isEnabled = false
                    view.statisticCt.setPinchZoom(false)
                    view.statisticCt.setScaleEnabled(false)
                    view.statisticCt.setTouchEnabled(false)
                    view.statisticCt.invalidate()
                }
            }
        }
        viewModel.getStatistics()
    }

}