package org.jxxy.debug

import android.content.Context
import android.util.Log
import com.igexin.sdk.PushManager
import com.igexin.sdk.message.GTCmdMessage
import com.igexin.sdk.message.GTNotificationMessage
import com.igexin.sdk.message.GTTransmitMessage


class GTIntentService : com.igexin.sdk.GTIntentService() {
    private val TAG = GTIntentService::class.java.simpleName

    override fun onReceiveServicePid(context: Context?, i: Int) {}

    // 接收 cid
    override fun onReceiveClientId(context: Context?, s: String) {
        Log.d(TAG, "onReceiveClientId=$s")
    }

    // 处理透传消息
    override fun onReceiveMessageData(context: Context?, msg: GTTransmitMessage) {
        val appid = msg.appid
        val taskid = msg.taskId
        val messageid = msg.messageId
        val payload = msg.payload
        val pkg = msg.pkgName
        val cid = msg.clientId


        // 第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
        val result =
            PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001)
        Log.d(TAG, "call sendFeedbackMessage = " + if (result) "success" else "failed")
        Log.d(
            TAG, """
     onReceiveMessageData -> appid = $appid
     taskid = $taskid
     messageid = $messageid
     pkg = $pkg
     cid = $cid
     """.trimIndent()
        )
        if (payload == null) {
            Log.e(TAG, "receiver payload = null")
        } else {
            val data = String(payload)
            Log.d(TAG, "receiver payload = $data")
        }
    }

    // cid 离线上线通知
    override fun onReceiveOnlineState(context: Context?, b: Boolean) {
        Log.d(TAG, "onReceiveOnlineState=$b")
    }

    // 各种事件处理回执
    override fun onReceiveCommandResult(context: Context?, gtCmdMessage: GTCmdMessage) {
        Log.d(TAG, "gtCmdMessage=$gtCmdMessage")
    }

    // 通知到达，只有个推通道下发的通知会回调此方法
    override fun onNotificationMessageArrived(
        context: Context?,
        gtNotificationMessage: GTNotificationMessage
    ) {
        Log.d(TAG, "onNotificationMessageArrived=" + gtNotificationMessage.content.toString())
    }

    // 通知点击，只有个推通道下发的通知会回调此方法
    override fun onNotificationMessageClicked(
        context: Context?,
        gtNotificationMessage: GTNotificationMessage
    ) {
        Log.d(TAG, "onNotificationMessageClicked=" + gtNotificationMessage.content.toString())
    }
}