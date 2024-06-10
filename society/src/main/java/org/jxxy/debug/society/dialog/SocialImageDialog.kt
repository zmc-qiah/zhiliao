package org.jxxy.debug.society.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import org.jxxy.debug.corekit.common.BaseDialog
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.society.databinding.DialogImgBinding

class SocialImageDialog(val image: ImageView) : BaseDialog<DialogImgBinding>() {
    override fun getView(inflater: LayoutInflater, parent: ViewGroup?): DialogImgBinding {
        return DialogImgBinding.inflate(layoutInflater, parent, false)
    }

    override fun initView(view: DialogImgBinding) {
        view.dialogImg .setImageDrawable(image.drawable)
        view.dialogImg .singleClick {
            dismiss()
        }
    }

}