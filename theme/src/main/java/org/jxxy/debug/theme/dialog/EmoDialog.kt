package org.jxxy.debug.theme.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import org.jxxy.debug.corekit.common.BaseApplication
import org.jxxy.debug.corekit.common.BaseDialog
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.theme.databinding.DialogEmoBinding

class EmoDialog () : BaseDialog<DialogEmoBinding>() {
    var way : () -> Unit = {

    }
    init {
        width = BaseApplication.context().resources.displayMetrics.widthPixels * 4 / 5
        ifCancelOnTouch = true
        enableBack = true
    }
    override fun getView(inflater: LayoutInflater, parent: ViewGroup?): DialogEmoBinding {
        return DialogEmoBinding.inflate(layoutInflater)
    }

    override fun initView(view: DialogEmoBinding) {
        view.dialogTv.text = "你今天的情绪：开心~\n" +
                "记录了这段对话的关键：我今天考试考了100分！\n" +
                "为您生成表情包:"
        view.dialogIv.load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/18/7fedf1fc-3cbd-4ef3-ac66-2d80009a1e35.jpg")
    }

    override fun onDestroy() {
        way()
        super.onDestroy()
    }
}