package org.jxxy.debug.member.activity

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.member.R
import org.jxxy.debug.member.bean.GroupRank
import org.jxxy.debug.member.databinding.ActivityGroupBankBinding
import org.jxxy.debug.member.databinding.ItemGroupBankBinding
import org.jxxy.debug.member.http.viewModel.PointViewModel
import org.jxxy.debug.member.viewHolder.GroupRankViewHolder

class GroupBankActivity : BaseActivity<ActivityGroupBankBinding>() {
    val viewModel: PointViewModel by lazy {
        ViewModelProvider(this).get(PointViewModel::class.java)
    }
    val adapter = object : SingleTypeAdapter<GroupRank>() {
        override fun createViewHolder(
            viewType: Int,
            inflater: LayoutInflater,
            parent: ViewGroup
        ): RecyclerView.ViewHolder? = GroupRankViewHolder(ItemGroupBankBinding.inflate(layoutInflater))
    }
    override fun bindLayout(): ActivityGroupBankBinding = ActivityGroupBankBinding.inflate(layoutInflater)

    override fun initView() {
        view.listRecyclerView.adapter = adapter
        view.listRecyclerView.addItemDecoration(CommonItemDecoration(5f))
        viewModel.getRank()
    }

    override fun subscribeUi() {
        viewModel.rankDataLiveData.observe(this) {
            it.onSuccess {
                it?.let {
                    view.groupBankCollapsingToolbarLayout.title = it.groupName
                    adapter.add(it.groupRankList)
                    val entries = mutableListOf<BarEntry>()
                    val list = mutableListOf<String>()
                    it.groupRankList?.forEach{
                        entries.add(BarEntry((it.position-1).toFloat(),it.score.toFloat()))
                        list.add(it.userName!!)
                    }
                    view.groupBankBarChart.apply {
                        val barDataSet = BarDataSet(entries, "")
                        val xAxis: XAxis = xAxis
//                        xAxis.axisMinimum = -0.25f
                        barDataSet.setColors(listOf(ResourceUtil.getColor(R.color.purple_new ),))
                        barDataSet.valueTextColor = Color.BLACK
                        barDataSet.valueTextSize = 12f
                        val barData = BarData(barDataSet)
                        data = barData
                        barData.barWidth = 0.5f
                        barDataSet.isHighlightEnabled = false
                        setDrawBarShadow(false)
                        setDrawValueAboveBar(true)
                        description.isEnabled = false
                        legend.isEnabled = false
                        animateY(1000, Easing.Linear)
                        animateX(1000, Easing.Linear)
                        xAxis.setAxisMaximum((entries.size-1).toFloat());
                        setVisibleXRange(0f,6f);
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
                }
            }
        }
    }
}
