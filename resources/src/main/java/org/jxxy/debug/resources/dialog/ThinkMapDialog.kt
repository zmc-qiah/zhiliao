package org.jxxy.debug.resources.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import org.jxxy.debug.common.util.getWidth
import org.jxxy.debug.corekit.common.BaseDialog
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.resources.databinding.DialogThinkMapBinding
import org.jxxy.debug.resources.myListener.ClickThinkMapDialogSubBtnListener

class ThinkMapDialog(val listener:ClickThinkMapDialogSubBtnListener):BaseDialog<DialogThinkMapBinding>() {
    init {
        width = getWidth()/10*8
        enableBack = true
    }
    override fun getView(inflater: LayoutInflater, parent: ViewGroup?): DialogThinkMapBinding = DialogThinkMapBinding.inflate(inflater,parent,false)
    var rootType = -1
    var aiType = 1
    override fun initView(view: DialogThinkMapBinding) {
        view.rootRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                view.allButton.id->{
                    rootType = 0
                }
                view.selectButton.id->{
                    rootType = 1
                }
                view.flagButton.id->{
                    rootType = 2
                }
            }
        }
        view.isAiRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                view.yesButton.id->{
                    aiType = 0
                }
                view.noButton.id->{
                    aiType = 1
                }
            }
        }
        view.submitButton.singleClick {
            if (rootType == -1){
                "请选择根节点".toast()
                return@singleClick
            }
            listener.click(rootType,if (aiType==0) true else false)
            dismiss()
        }
    }
}