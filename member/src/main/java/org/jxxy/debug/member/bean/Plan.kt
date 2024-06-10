package org.jxxy.debug.member.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType
import org.jxxy.debug.member.util.MyType

class Plan(
    val planId: Long = -1,
    var planName: String ? = null,
    var planState: Long ? = null,
    var startTime: String ? = null,
    var endTime: String ? = null
):MultipleType {
    var name: String ? = null
        get() = field ?: planName
    var state: Long? = null
        get() = field ?: planState
    var studyEvent: ArrayList<PlanEvent>? = null
    private var events: ArrayList<PlanEvent>? = ArrayList()
    var planPosts: ArrayList<PlanEvent> ? = null

//    var studyEvent: ArrayList<PlanEvent>? get() = events
//        set(value) {
//            events = value
//        }
//    private var events: ArrayList<PlanEvent>? = ArrayList()
//    var planPosts: ArrayList<PlanEvent>?
//        get() = events
//        set(value) {
//            events = value
//        }
    constructor() : this(-1, "", null, "", null)

    override fun toString(): String {
        return "Plan(planId=$planId, state=$state, name=$name, startTime=$startTime, endTime=$endTime, studyEvent=$studyEvent)"
    }

    override fun viewType(): Int = MyType.MemberPlan
}
