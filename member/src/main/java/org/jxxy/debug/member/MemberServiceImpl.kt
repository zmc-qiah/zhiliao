package org.jxxy.debug.member

import android.content.Context
import com.google.auto.service.AutoService
import org.jxxy.debug.common.service.MemberService
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.member.activity.PlanActivity

@AutoService(MemberService::class)
class MemberServiceImpl:MemberService {
    override fun goPlan(context: Context) {
        context.startActivity<PlanActivity>()
    }
}