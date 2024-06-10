package org.jxxy.debug.member.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class PlanEvent(
    val eventId: Long? = null,
    var eventName: String? = null,
    val eventState: Long? = null,
    var eventTime: String? = null,
    var type: Int = 1
) : MultipleType {
    constructor() : this(0, "", 0, "") {
        this.type = 1
    }
    override fun viewType(): Int = type
    override fun toString(): String {
        return "PlanEvent(eventId=$eventId, eventName=$eventName, eventState=$eventState, eventTime=$eventTime, type=$type)"
    }
}
