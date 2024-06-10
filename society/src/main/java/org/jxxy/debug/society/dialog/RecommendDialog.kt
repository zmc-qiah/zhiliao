package org.jxxy.debug.society.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import org.jxxy.debug.corekit.common.BaseDialog
import org.jxxy.debug.society.databinding.DialogRecommendBinding

class RecommendDialog:BaseDialog<DialogRecommendBinding>() {
    init {
        ifCancelOnTouch = true
        enableBack = true
    }
    override fun getView(inflater: LayoutInflater, parent: ViewGroup?): DialogRecommendBinding {
        return DialogRecommendBinding.inflate(inflater)
    }

    override fun initView(view: DialogRecommendBinding) {
        view.textView2.text="123456"
    }
}