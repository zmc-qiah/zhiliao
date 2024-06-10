package org.jxxy.debug.test.fragment.fragment

import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import org.jxxy.debug.corekit.common.BaseDialog
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.test.databinding.FragmentPkBinding

class PKFragmentDialog(val type : Int) : BaseDialog<FragmentPkBinding>() {

    var parentWidth = 0
    var parentHeight = 0

    init {
        ifCancelOnTouch = true
        enableBack = true

    }

    override fun getView(inflater: LayoutInflater, parent: ViewGroup?): FragmentPkBinding {
        return FragmentPkBinding.inflate(inflater,parent,false)
    }

    override fun initView(view: FragmentPkBinding) {
        when(type){
            1 -> view.pkFinishTv.text = "恭喜,你赢了!!"
            0 -> view.pkFinishTv.text = "别灰心，下次努力!!"
        }
    }
}