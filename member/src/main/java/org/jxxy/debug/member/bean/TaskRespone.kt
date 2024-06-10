package org.jxxy.debug.member.bean

class TaskRespone(
    val taskDetails: List<PointTask> ?,
    val todaySums: Long?,
    val scoreSums: Long?,
    val togetherDays: String?
){
    constructor():this(null,null,null,null)

    override fun toString(): String {
        return "TaskRespone(taskDetails=$taskDetails, todaySums=$todaySums, scoreSums=$scoreSums, togetherDays=$togetherDays)"
    }

}
