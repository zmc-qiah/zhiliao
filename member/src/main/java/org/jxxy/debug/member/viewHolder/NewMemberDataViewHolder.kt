package org.jxxy.debug.member.viewHolder

import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import org.jxxy.debug.common.service.loginCheck
import org.jxxy.debug.common.util.MoreViewAlpha0to1
import org.jxxy.debug.common.util.MoreViewAlpha1to0
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.member.R
import org.jxxy.debug.member.activity.ResourcesActiviy
import org.jxxy.debug.member.bean.Member4Part
import org.jxxy.debug.member.bean.MemberData
import org.jxxy.debug.member.bean.MemberGroup
import org.jxxy.debug.member.databinding.ItemMember4partBinding
import org.jxxy.debug.member.databinding.ItemMemberDataBinding
import org.jxxy.debug.member.databinding.ItemMemberGroupBankBinding
import org.jxxy.debug.member.databinding.ItemNewMemberDataBinding
import org.jxxy.debug.member.util.MyType

class NewMemberDataViewHolder(binding: ItemNewMemberDataBinding, private val onClick: (view: View) -> Unit) : MultipleViewHolder2<ItemNewMemberDataBinding, MemberData>(binding) {
    val pieList = ArrayList<PieEntry>()
    override fun setHolder(entity: MemberData) {
        loadPie(entity.mebMember4Part)
        loadBar(entity.group)
        loadWeek()
    }

    private fun loadWeek() {

        view.run {
            val list = listOf<List<View>>(
                listOf(aTv),
                listOf(cntIcon,aTv3,aTv2),
                listOf(timeIcon,cntTV,aTv4),
                listOf(userWeeklyButton)
            )
            MoreViewAlpha0to1(list,250)
        }
    }

    private fun loadPie(entity: Member4Part) {
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

    private fun loadBar(entity: MemberGroup) {
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

    private fun initPie(list: List<PieEntry>) {
        view.userRescourcesPieC.apply {
            dragDecelerationFrictionCoef = 0.5f
            rotationAngle = 315f
            isRotationEnabled = true
            isHighlightPerTapEnabled = true
            val centerX = width / 2f
            val centerY = height / 2f
            animateXY(1000, 1000)
            val pieDataSet = PieDataSet(list, "")
            pieDataSet.apply {

                val customColors = mutableListOf<Int>(
                    ResourceUtil.getColor(R.color.pie_1),
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
                    loginCheck(context){
                        e as PieEntry
                        val intent = Intent(context, ResourcesActiviy::class.java)
                        intent.putExtra("page", e.label)
                        context.startActivity(intent)
                    }
                }
                override fun onNothingSelected() {
                }
            })
            invalidate()
        }
    }
    private fun initBar(list: List<String>, bars: List<BarEntry>) {
        val barChart = view.groupBar
        barChart.apply {
            val entries = bars
            val barDataSet = BarDataSet(entries, "Bar Data")
            barDataSet.setColors(
                listOf(
                    ResourceUtil.getColor(R.color.point_2),
                    ResourceUtil.getColor(R.color.point_1),
                    ResourceUtil.getColor(R.color.point_3)
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