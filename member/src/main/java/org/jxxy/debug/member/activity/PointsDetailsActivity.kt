package org.jxxy.debug.member.activity

import androidx.lifecycle.ViewModelProvider
import org.jxxy.debug.common.util.getHeight
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.common.BaseApplication
import org.jxxy.debug.member.adapter.PointDetailAdapter
import org.jxxy.debug.member.bean.PointDetailChild
import org.jxxy.debug.member.bean.PointDetailGroup
import org.jxxy.debug.member.databinding.ActivityPointsDetailsBinding
import org.jxxy.debug.member.http.viewModel.PointViewModel

class PointsDetailsActivity : BaseActivity<ActivityPointsDetailsBinding>() {
    val viewModel by lazy {
        ViewModelProvider(this).get(PointViewModel::class.java)
    }
    override fun bindLayout(): ActivityPointsDetailsBinding = ActivityPointsDetailsBinding.inflate(layoutInflater)

    override fun initView() {
        val height = getHeight() - view.pointDetailsToolbar.height
        view.pointsDetails.layoutParams.height = height

    }

    override fun subscribeUi() {
        viewModel.detailDataLiveData.observe(this){
            it.onSuccess {a->
                a
                a?.list?.let {
                    it
                    view.pointsDetails.setAdapter(PointDetailAdapter(it))
                }
            }
        }
        viewModel.getDetail()
    }
}
