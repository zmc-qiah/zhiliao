package org.jxxy.debug.member.activity

import androidx.lifecycle.ViewModelProvider
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.member.adapter.PointTaskAdapter
import org.jxxy.debug.member.databinding.ActivityPointsTaskBinding
import org.jxxy.debug.member.http.viewModel.PointViewModel

class PointsTaskActivity : BaseActivity<ActivityPointsTaskBinding>() {
    val adapter: PointTaskAdapter by lazy { PointTaskAdapter() }
    val viewModel: PointViewModel by lazy {
        ViewModelProvider(this).get(PointViewModel::class.java)
    }
    override fun bindLayout(): ActivityPointsTaskBinding = ActivityPointsTaskBinding.inflate(layoutInflater)

    override fun initView() {
        view.pointTasks.adapter = adapter
        view.pointTasks.addItemDecoration(CommonItemDecoration(10f))
        viewModel.getTask()
    }
    override fun subscribeUi() {
        viewModel.taskDataLiveData.observe(this) {
            it.response?.let {
                var currentPoint: Int = 0
                var maxPoint: Int = 0
                it.taskDetails?.let {
                    it.forEach {
                        currentPoint += it.currentPoint ?: 0
                        maxPoint += it.maxPoint ?: 0
                    }
                    view.pointToday.text = "今日积分$currentPoint/$maxPoint"
                    adapter.add(it)
                }
                it.scoreSums?.let {
                    view.pointSum.text = it.toString() + "积分"
                }
                it.togetherDays?.let {
                    view.pointDays.text = "app陪伴学习${it}天"
                }
            }
        }
    }
}
