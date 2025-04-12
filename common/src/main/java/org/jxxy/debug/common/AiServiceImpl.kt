package org.jxxy.debug.common

import android.content.Context
import com.google.auto.service.AutoService
import org.jxxy.debug.common.service.AiService
import org.jxxy.debug.corekit.common.BaseApplication
import org.jxxy.debug.corekit.util.toast

@AutoService(AiService::class)
class AiServiceImpl : AiService {
    override fun goAiPaint(context: Context) {
        BaseApplication.context().toast("Not yet implemented")
    }

    override fun goAiAdv(context: Context, string: String) {
        BaseApplication.context().toast("Not yet implemented")
    }

    override fun goAiAdv(context: Context) {
        BaseApplication.context().toast("Not yet implemented")
    }

    override fun goAiEmo(context: Context) {
        BaseApplication.context().toast("Not yet implemented")
    }

    override fun goPose(context: Context) {
        BaseApplication.context().toast("Not yet implemented")
    }

    override fun goNavigation(context: Context) {
        BaseApplication.context().toast("Not yet implemented")
    }

    override fun goOCR(context: Context) {
        BaseApplication.context().toast("Not yet implemented")
    }

    override fun goKnowledgeBase(context: Context) {
        BaseApplication.context().toast("Not yet implemented")
    }

    override fun goGuessing(context: Context) {
        BaseApplication.context().toast("Not yet implemented")
    }
}