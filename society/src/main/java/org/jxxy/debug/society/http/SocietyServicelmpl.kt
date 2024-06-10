package org.jxxy.debug.society.http

import android.content.Context
import com.google.auto.service.AutoService
import org.jxxy.debug.common.service.SocietyService
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.society.activity.DiscussActivity
import org.jxxy.debug.society.activity.ExcellentClassroomActivity
import org.jxxy.debug.society.activity.ExperienceActivity
import org.jxxy.debug.society.activity.HistoryActivity
import org.jxxy.debug.society.activity.LectureActivity
import org.jxxy.debug.society.activity.MovableActivity
import org.jxxy.debug.society.activity.PlayActivity
import org.jxxy.debug.society.activity.PracticeActivity
import org.jxxy.debug.society.activity.RecommendActivity


@AutoService(SocietyService::class)
class SocietyServicelmpl:SocietyService {

    override fun goDiscuss(context: Context) {
        context.startActivity<DiscussActivity>()
    }
    override fun goExcellentClassroom(context: Context) {
        context.startActivity<ExcellentClassroomActivity>()
    }
    override fun goExperience(context: Context) {
        context.startActivity<ExperienceActivity>()
    }
    override fun goHistory(context: Context) {
        context.startActivity<HistoryActivity>()
    }
    override fun goLecture(context: Context) {
        context.startActivity<LectureActivity>()
    }
    override fun goMovable(context: Context) {
        context.startActivity<MovableActivity>()
    }
    override fun goPlay(context: Context) {
        context.startActivity<PlayActivity>()
    }
    override fun goPractice(context: Context) {
        context.startActivity<PracticeActivity>()
    }
    override fun goRecommend(context: Context) {
        context.startActivity<RecommendActivity>()
    }


}


