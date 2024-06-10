package org.jxxy.debug.test.fragment.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import org.jxxy.debug.corekit.common.BaseDialog
import org.jxxy.debug.test.databinding.DialogTheBestScoreBinding
import org.jxxy.debug.test.fragment.bean.TheBestScore

class TheBestScoreDialog(val score : TheBestScore) : BaseDialog<DialogTheBestScoreBinding>() {
    init {
        ifCancelOnTouch = true
        enableBack = true
    }
    override fun getView(inflater: LayoutInflater, parent: ViewGroup?): DialogTheBestScoreBinding {
        return DialogTheBestScoreBinding.inflate(inflater,parent,false)
    }

    override fun initView(view: DialogTheBestScoreBinding) {
        view.specialTimeTv.text = score.pastTime
        view.specialDamageTv.text = score.damage
        view.specialPercentTv.text = score.accuracy
    }
}