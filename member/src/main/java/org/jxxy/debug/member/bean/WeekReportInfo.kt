package org.jxxy.debug.member.bean

class WeekReportInfo {
    var studyTime: String? = null
    var dayStudyTime: String? = null
    var studyTask: String? = null
    var noFinishPoint: Int = -1
    var getScore: Int = -1
    var consumeScore: Int = -1
    var answerSum: Int = -1
    var answerRight: String? = null
    var readSum: Int = -1
    var videoTime: String? = null
    var aiTime: String? = null
    var aiStudyTime: String? = null

    override fun toString(): String {
        return "WeekReportInfo(studyTime=$studyTime, dayStudyTime=$dayStudyTime, " +
            "studyTask=$studyTask, noFinishPoint=$noFinishPoint, getScore=$getScore, " +
            "consumeScore=$consumeScore, answerSum=$answerSum, answerRight=$answerRight, " +
            "readSum=$readSum, videoTime=$videoTime, aiTime=$aiTime, aiStudyTime=$aiStudyTime)"
    }
}
