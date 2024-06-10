package org.jxxy.debug.member.bean

class MemberScore(
    val day7Score: List<DayScore>?,
    val sumsScore: Long,
    val todayScore: Long
) {
    constructor() : this(null, -1, -1)

    override fun toString(): String {
        return "MemberScore(day7Score=$day7Score, sumsScore=$sumsScore, todayScore=$todayScore)"
    }

}
