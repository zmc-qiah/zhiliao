package org.jxxy.debug.theme.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import org.jxxy.debug.corekit.common.BaseApplication
import org.jxxy.debug.corekit.common.BaseDialog
import org.jxxy.debug.theme.databinding.DialogRuleBinding

class ChatGameDialog(val title : String,val rule : String) : BaseDialog<DialogRuleBinding>(){
    init {
        width = BaseApplication.context().resources.displayMetrics.widthPixels * 2 / 3
        height = BaseApplication.context().resources.displayMetrics.heightPixels * 2 / 5
        ifCancelOnTouch = true
        enableBack = true
    }
    override fun getView(inflater: LayoutInflater, parent: ViewGroup?): DialogRuleBinding {
        return DialogRuleBinding.inflate(inflater,parent,false)
    }

    override fun initView(view: DialogRuleBinding) {
        view.ruleTitleTv.text = title
        view.ruleContentTv.text = rule
    }
}