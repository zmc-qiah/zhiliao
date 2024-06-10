package org.jxxy.debug.search.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import org.jxxy.debug.corekit.common.BaseDialog
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.search.databinding.DialogImageBinding


class SearchImageDialog(val image: ImageView) : BaseDialog<DialogImageBinding>() {
    init {
        enableBack = true
    }
    override fun getView(inflater: LayoutInflater, parent: ViewGroup?): DialogImageBinding {
        return DialogImageBinding.inflate(layoutInflater, parent, false)
    }

    override fun initView(view: DialogImageBinding) {
        view.dialogImg .setImageDrawable(image.drawable)
        view.dialogImg .singleClick {
            dismiss()
        }
    }

}