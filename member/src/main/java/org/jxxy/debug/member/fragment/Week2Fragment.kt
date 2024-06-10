package org.jxxy.debug.member.fragment

import android.animation.ObjectAnimator
import org.jxxy.debug.member.R
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.*
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.member.databinding.FragmentWeek2Binding
import org.jxxy.debug.member.http.respone.WeekReportResponce
import java.util.*

class Week2Fragment(val info: WeekReportResponce) : BaseFragment<FragmentWeek2Binding>() {
    val pieList by lazy { ArrayList<PieEntry>() }
    val pieChart by lazy { find.poinyWeekChange }
    init {
        pieList.add(PieEntry(info.weekScoreInfo!!.allScore.toFloat(), "总积分"))
        pieList.add(PieEntry(info.weekScoreInfo!!.getScore.toFloat(), "新获得积分"))
    }
    override fun bindLayout(): FragmentWeek2Binding = FragmentWeek2Binding.inflate(layoutInflater)

    override fun initView() {
        initPie(pieList)
        find.weekly2Point1.text = info.weekScoreInfo!!.getScore.toString()
        find.weekly2Point2.text = info.weekScoreInfo!!.allScore.toString()
    }

    private fun initPie(list: List<PieEntry>) {
        pieChart.apply {
            dragDecelerationFrictionCoef = 0.5f
            rotationAngle = 270f
            isRotationEnabled = true
            isHighlightPerTapEnabled = true
            setExtraOffsets(5f, 5f, 5f, 5f)
            animateXY(1000, 1000)
            val pieDataSet = PieDataSet(list, "")
            pieDataSet.apply {
                val customColors = mutableListOf<Int>(
                    ResourceUtil.getColor(
                        R.color.task_begin
                    ),
                    ResourceUtil.getColor(R.color.task_button)

                )
                this.colors = customColors
                sliceSpace = 2f
                selectionShift = 8f
                valueTextSize = 0f
                description = null
                setEntryLabelTextSize(16f)
            }
            data = PieData(pieDataSet)
            invalidate()
        }
        CoroutineScope(Dispatchers.Default).launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val fadeInAnimator = ObjectAnimator.ofFloat(find.weekly2Text3, "alpha", 0f, 1f)
                fadeInAnimator.duration = 3000 // 设置动画持续时间，单位为毫秒
                fadeInAnimator.start()
            }
        }
    }

    override fun subscribeUi() {
    }
}
