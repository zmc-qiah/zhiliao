package org.jxxy.debug.resources.dialog

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import org.jxxy.debug.corekit.common.BaseDialog
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.resources.bean.ChapterCommentBody
import org.jxxy.debug.resources.bean.ItemCommentedChapter
import org.jxxy.debug.resources.databinding.DialogCommentBinding
import org.jxxy.debug.resources.databinding.ItemCommentedTextBinding
import org.jxxy.debug.resources.http.viewModel.ResourceViewModel
import org.jxxy.debug.resources.myListener.SubmitListener

class CRCD(val icc: ItemCommentedChapter,val a: (s:String)->Unit):BaseDialog<DialogCommentBinding>() {
    init {
        gravity = Gravity.BOTTOM
        enableBack = true
        ifCancelOnTouch = true
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED)
    }
    override fun getView(inflater: LayoutInflater, parent: ViewGroup?): DialogCommentBinding =
        DialogCommentBinding.inflate(layoutInflater, parent, false)

    override fun initView(view: DialogCommentBinding) {
        view.submitComment.singleClick{
            val string = view.editComment.text.toString()
            if (string.length == 0){
                "请输入章评".toast()
            }else{
                view.editComment.setText("")
                a(string)
                dismiss()
            }
        }
    }
}