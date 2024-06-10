package org.jxxy.debug.member.bean

class DayScore(
    var data: String,
    val sums: Long
) {

    constructor() : this("", 0)

    override fun toString(): String {
        return "DayScore(time='$data', score=$sums)"
    }
}
