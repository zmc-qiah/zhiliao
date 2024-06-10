package org.jxxy.debug.theme.viewHolder

import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.theme.bean.AiGuessGame
import org.jxxy.debug.theme.databinding.ItemAiGuessGameBinding

class AiGuessGameViewHolder(view : ItemAiGuessGameBinding) : MultipleViewHolder2<ItemAiGuessGameBinding,AiGuessGame>(view) {
    override fun setHolder(entity: AiGuessGame) {
        view.aiGuessIv.load("https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/14/2167d52a-cf27-41bc-825a-9aa1493390f4.jpg",5)
    }
}