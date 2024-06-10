package org.jxxy.debug.theme.viewHolder

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.View
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import org.jxxy.debug.common.util.MoreViewAlpha0to1
import org.jxxy.debug.common.util.MoreViewAlpha1to0
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.theme.bean.AIPose
import org.jxxy.debug.theme.databinding.ItemThemePoseBinding

class AiPoseViewHolder(view:ItemThemePoseBinding):MultipleViewHolder2<ItemThemePoseBinding,AIPose>(view) {
    override fun setHolder(entity: AIPose) {
        initLine(
            listOf("8:00","9:00","10:00","11:00"),
            listOf(
                Entry(0f,1f),
                Entry(0.09f,1f),
                Entry(0.1f,2f),
                Entry(0.15f,2f),
                Entry(0.28f,2f),
                Entry(0.29f,1f),
                Entry(0.38f,1f),
                Entry(0.39f,2f),
                Entry(0.58f,2f),
                Entry(0.59f,1f),
                Entry(0.79f,1f),
                Entry(0.8f,2f),
                Entry(0.88f,2f),
                Entry(0.89f,1f),
                Entry(1.39f,1f),
                Entry(1.4f,2f),
                Entry(1.55f,2f),
                Entry(1.56f,1f),
                Entry(1.79f,1f),
                Entry(1.8f,2f),
                Entry(1.83f,1f),
                Entry(2.29f,1f),
                Entry(2.3f,2f),
                Entry(2.6f,2f),
                Entry(2.61f,1f),
                Entry(2.68f,1f),
                Entry(2.69f,2f),
                Entry(2.7f,2f),
                Entry(2.75f,1f),
                Entry(2.79f,1f),
                Entry(2.8f,2f),
                Entry(2.85f,2f),
                Entry(2.86f,1f),
                Entry(3f,1f),
            )
        )
        val goodSpan = SpannableString("92%")
        goodSpan.setSpan(RelativeSizeSpan(1.5f), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        goodSpan.setSpan(ForegroundColorSpan(ResourceUtil.getColor(org.jxxy.debug.corekit.R.color.color_green)), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        view.goodValueIv.text = goodSpan
        val badSpan = SpannableString("8%")
        badSpan.setSpan(RelativeSizeSpan(1.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        badSpan.setSpan(ForegroundColorSpan(ResourceUtil.getColor(org.jxxy.debug.corekit.R.color.wrong_color)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        view.badValueIv.text = badSpan
        val value1Span = SpannableString("3小时8分")
        value1Span.setSpan(RelativeSizeSpan(1.5f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        value1Span.setSpan(ForegroundColorSpan(ResourceUtil.getColor(org.jxxy.debug.corekit.R.color.color_green)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        value1Span.setSpan(RelativeSizeSpan(1.5f), 3, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        value1Span.setSpan(ForegroundColorSpan(ResourceUtil.getColor(org.jxxy.debug.corekit.R.color.color_green)), 3, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        view.value1Iv.text = value1Span
        val value2Span = SpannableString("15次")
        value2Span.setSpan(RelativeSizeSpan(1.5f), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        value2Span.setSpan(ForegroundColorSpan(ResourceUtil.getColor(org.jxxy.debug.corekit.R.color.wrong_color)), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        view.value2Iv.text = value2Span
        val value3Span = SpannableString("20%")
        value3Span.setSpan(RelativeSizeSpan(1.5f), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        value3Span.setSpan(ForegroundColorSpan(ResourceUtil.getColor(org.jxxy.debug.corekit.R.color.color_green)), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        view.value3Iv.text = value3Span
        val value4Span = SpannableString("脖子前伸/12次")
        value4Span.setSpan(RelativeSizeSpan(1.5f), 5, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        value4Span.setSpan(ForegroundColorSpan(ResourceUtil.getColor(org.jxxy.debug.corekit.R.color.wrong_color)), 5, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        view.value4Iv.text = value4Span

    }
    private val tempList = listOf<String>("","标准坐姿","不良坐姿")
    fun initLine(list: List<String>, entryList: List<Entry>) {
        val tally = view.lineChart
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
            axisLeft.granularity = 1f
            axisLeft.valueFormatter = object : ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    return tempList.get((value).toInt())
                }
            }

            axisRight.isEnabled = false
            description.isEnabled = false
            isDragEnabled = false
            isScaleXEnabled = false
            isScaleYEnabled = false
            isHighlightPerTapEnabled = false
        }
        val lineDataSet = LineDataSet(entryList, null)
        lineDataSet.apply {
            color = ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_200)
            lineWidth = 2f
            setDrawCircles(false)
            setDrawValues(false)
//            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            mode = LineDataSet.Mode.STEPPED
        }
        val LineData = LineData(lineDataSet)
        tally.data = LineData
        tally.invalidate()
    }
    override fun setHolder(entity: AIPose, payload: Any) {
        if (payload as? Boolean?:false){
            view.run {
                val list = listOf<List<View>>(
                    listOf(
                        title,line
                    ),
                    listOf(
                      view1,view2,view3,rc
                    ),
                    listOf(
                        aTV
                    ),
                    listOf(
                        goodKeyIv,goodValueIv,badKeyIv,badValueIv,progress
                    ),
                    listOf(
                        lineChart
                    ),
                    listOf(
                        chatTv,postTv,view4
                    ),
                    listOf(
                        icon1,key1Iv,value1Iv
                    ),
                    listOf(
                        icon2,key2Iv,value2Iv
                    ),
                    listOf(
                        icon3,key3Iv,value3Iv
                    ),
                    listOf(
                        icon4,key4Iv,value4Iv
                    ),
                )
                MoreViewAlpha0to1(list)
            }
        }else{
            view.run {
                val list = listOf<List<View>>(
                    listOf(
                        title,line
                    ),
                    listOf(
                        view1,view2,view3,rc
                    ),
                    listOf(
                        aTV
                    ),
                    listOf(
                        goodKeyIv,goodValueIv,badKeyIv,badValueIv,progress
                    ),
                    listOf(
                        lineChart
                    ),
                    listOf(
                        chatTv,postTv,view4
                    ),
                    listOf(
                        icon1,key1Iv,value1Iv
                    ),
                    listOf(
                        icon2,key2Iv,value2Iv
                    ),
                    listOf(
                        icon3,key3Iv,value3Iv
                    ),
                    listOf(
                        icon4,key4Iv,value4Iv
                    ),
                )
                MoreViewAlpha1to0(list.reversed())
            }
        }
    }
}
