package org.jxxy.debug.push

import android.content.Context
import android.util.Log
import com.google.gson.JsonSyntaxException
import com.igexin.sdk.GTIntentService
import com.igexin.sdk.message.GTCmdMessage
import com.igexin.sdk.message.GTNotificationMessage
import com.igexin.sdk.message.GTTransmitMessage
import com.orhanobut.logger.Logger
import org.jxxy.debug.corekit.gson.GsonManager
import org.jxxy.debug.push.getui.GeTuiConstants
import org.jxxy.debug.push.getui.GeTuiPushFactory
import org.jxxy.debug.push.gson.GeTuiBaseBean
import org.jxxy.debug.push.gson.PushCardDefaultEntity

class GeTuiIntentService : GTIntentService() {
    override fun onReceiveServicePid(context: Context?, pid: Int) {}

    // 处理透传消息
    override fun onReceiveMessageData(context: Context?, msg: GTTransmitMessage?) {
        // 透传消息的处理，详看 SDK demo
        val jsonString = msg?.payload?.let { payLoad ->
            val string = String(payLoad)
            string
        }
        Log.d("CID Test","${msg?.clientId}")
        Logger.w(GeTuiConstants.LOG_TAG, "onReceiveMessageData: $jsonString")
        try {
            val entity = GsonManager.instance.gson.fromJson(jsonString, PushCardDefaultEntity::class.java)
            entity.messageId = msg?.messageId
            entity.taskId = msg?.taskId
            GeTuiPushFactory.instance.handlePush(entity)
            return
        } catch (e: JsonSyntaxException) {
            Logger.e(GeTuiConstants.LOG_TAG, "json解析异常: ", e)
        } catch (e: Exception) {
            Logger.e(GeTuiConstants.LOG_TAG, "onReceiveMessageData: ", e)
        }
    }

    // 接收 cid
    override fun onReceiveClientId(context: Context?, clientid: String) {
        Log.d("CID Test","$clientid")
    }

    // cid 离线上线通知
    override fun onReceiveOnlineState(context: Context?, online: Boolean) {}

    // 各种事件处理回执
    override fun onReceiveCommandResult(context: Context?, cmdMessage: GTCmdMessage?) {}

    // 通知到达，只有个推通道下发的通知会回调此方法
    override fun onNotificationMessageArrived(context: Context?, msg: GTNotificationMessage?) {
    }

    // 通知点击，只有个推通道下发的通知会回调此方法
    override fun onNotificationMessageClicked(context: Context?, msg: GTNotificationMessage?) {
    }
}
