package org.jxxy.debug.push.getui

import org.jxxy.debug.push.card.CardManagerListener
import org.jxxy.debug.push.card.PushCardManager
import org.jxxy.debug.push.gson.GeTuiBaseBean
import org.jxxy.debug.push.gson.PushCardBaseBean
import java.util.concurrent.ConcurrentLinkedQueue

class GeTuiPushCardImpl :
    GeTuiPushBaseImpl(), CardManagerListener {
    private val queue: ConcurrentLinkedQueue<PushCardBaseBean> = ConcurrentLinkedQueue()
    private val manager: PushCardManager by lazy { PushCardManager.instance }

    init {
        manager.listener = this
    }

    override fun handlePush(entity: GeTuiBaseBean) {
        super.handlePush(entity)
        if (entity !is PushCardBaseBean) return
        queue.offer(entity)
        showNext()
    }

    override fun dismiss() {
        showNext()
    }

    override fun onPageChange() {
        showNext()
    }

    private fun showNext() {
        if (manager.isShowing) {
            return
        }
        // 如果没有弹出的卡片
        queue.peek()?.let {
            if (manager.showCard(it)) {
                // 如果展示成功，则移出队列
                queue.poll()
            }
        }
    }
}
