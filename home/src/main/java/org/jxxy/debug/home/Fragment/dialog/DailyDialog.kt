package org.jxxy.debug.home.Fragment.dialog

import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import org.jxxy.debug.corekit.common.BaseDialog
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.home.databinding.DialogDailyBinding
import org.jxxy.debug.home.databinding.DialogLawBinding

class DailyDialog : BaseDialog<DialogDailyBinding>() {

    init {
        ifCancelOnTouch = true
        enableBack = true
    }

    override fun getView(inflater: LayoutInflater, parent: ViewGroup?): DialogDailyBinding {
        return DialogDailyBinding.inflate(inflater)
    }

    override fun initView(view: DialogDailyBinding) {
        view.lawText.singleClick {
            val cmb: ClipboardManager =
                context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cmb.text = view.lawText.text
            "已复制".toast()
        }
    }
}
