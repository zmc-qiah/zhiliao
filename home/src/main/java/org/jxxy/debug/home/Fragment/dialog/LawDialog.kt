package org.jxxy.debug.home.Fragment.dialog

import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import org.jxxy.debug.corekit.common.BaseDialog
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.home.databinding.DialogLawBinding

class LawDialog : BaseDialog<DialogLawBinding>() {

    init {
        ifCancelOnTouch = true
        enableBack = true
    }

    override fun getView(inflater: LayoutInflater, parent: ViewGroup?): DialogLawBinding {
        return DialogLawBinding.inflate(inflater)
    }

    override fun initView(view: DialogLawBinding) {
        view.lawText.singleClick {
            val cmb: ClipboardManager =
                context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cmb.text = view.lawText.text
            "已复制".toast()
        }
    }
}
