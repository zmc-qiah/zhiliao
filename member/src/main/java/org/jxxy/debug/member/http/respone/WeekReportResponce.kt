package org.jxxy.debug.member.http.respone

import org.jxxy.debug.member.bean.WeekAchievementInfo
import org.jxxy.debug.member.bean.WeekReportInfo
import org.jxxy.debug.member.bean.WeekScoreInfo
import org.jxxy.debug.member.bean.WeekStudyInfo

class WeekReportResponce {
    var weekReportInfo: WeekReportInfo? = null
    var weekScoreInfo: WeekScoreInfo? = null
    var weekStudyInfos: List<WeekStudyInfo>? = null
    var weekAchievementInfos: List<WeekAchievementInfo>? = null

    override fun toString(): String {
        return "Data(weekReportInfo=$weekReportInfo, weekScoreInfo=$weekScoreInfo, " +
            "weekStudyInfos=$weekStudyInfos, weekAchievementInfos=$weekAchievementInfos)"
    }
}
