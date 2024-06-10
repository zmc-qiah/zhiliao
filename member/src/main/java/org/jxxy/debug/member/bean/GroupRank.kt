package org.jxxy.debug.member.bean

class GroupRank(
    val position: Long,
    val score: Long,
    val userName: String? = null,
    val userHead: String? = null
) {
    constructor() : this(0, 0, "")
}
