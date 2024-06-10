package org.jxxy.debug.member.bean

class PostPlan(var planPosts: ArrayList<PlanEvent>?) {
    constructor() : this(ArrayList())

    override fun toString(): String {
        return "PostPlan(planPosts=$planPosts)"
    }

}
