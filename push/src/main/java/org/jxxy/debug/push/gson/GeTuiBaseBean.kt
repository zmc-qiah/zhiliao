package org.jxxy.debug.push.gson

import org.jxxy.debug.common.scheme.Scheme

const val PUSH_CARD_COUPON = "coupon"
const val PUSH_CARD_DEFAULT = "default"
const val PUSH_CARD_ORDER = "order_status_change_notice"

open class GeTuiBaseBean(val type: String?) {
    var taskId: String? = null
    var messageId: String? = null
    override fun toString(): String {
        return "GeTuiBaseBean(type=$type, taskId=$taskId, messageId=$messageId)"
    }
}

open class PushCardBaseBean(type: String?) : GeTuiBaseBean(type) {
    val extras: PushCardExtras? = null
    override fun toString(): String {
        return "PushCardBaseBean(extras=$extras)"
    }
}

class PushCardDefaultEntity(
    val title: String?,
    val content: String?,
    val buttonDesc: String?,
    val iconUrl: String?,
    val skipUrl: Scheme?,
    val show : Int,
    val beginTime : String? = null,
    val endTime : String? = null
) : PushCardBaseBean(PUSH_CARD_DEFAULT) {
    override fun toString(): String {
        return "PushCardDefaultEntity(title=$title, content=$content, buttonDesc=$buttonDesc, iconUrl=$iconUrl, skipUrl=$skipUrl)"
    }
}

class PushCardExtras(
    // 任务id
    val yh_msgID: String?,
    val yh_nodeId: String?,
    val yh_viewType: String?,
    val yh_serviceName: String?,
    val yh_moduleType: String?
) {
    override fun toString(): String {
        return "PushCardExtras(yh_msgID=$yh_msgID, yh_nodeId=$yh_nodeId, yh_viewType=$yh_viewType, yh_serviceName=$yh_serviceName, yh_moduleType=$yh_moduleType)"
    }
}
