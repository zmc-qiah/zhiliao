package org.jxxy.debug.common.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import org.jxxy.debug.common.databinding.DialogImageClickBinding
import org.jxxy.debug.corekit.common.BaseDialog
import org.jxxy.debug.corekit.util.singleClick

class ImageClickDialog(val image: ImageView) : BaseDialog<DialogImageClickBinding>() {
    init {
        enableBack = true
    }
    override fun getView(inflater: LayoutInflater, parent: ViewGroup?): DialogImageClickBinding = DialogImageClickBinding.inflate(inflater, parent, false)

    override fun initView(view: DialogImageClickBinding) {
        view.imageView.setImageDrawable(image.drawable)
        view.imageView.singleClick {
            dismiss()
        }

    }
}
