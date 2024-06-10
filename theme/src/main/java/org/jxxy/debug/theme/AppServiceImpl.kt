package org.jxxy.debug.theme

import android.content.Context
import android.content.Intent
import com.google.auto.service.AutoService
import org.jxxy.debug.common.service.AiService
import org.jxxy.debug.common.service.WebService
import org.jxxy.debug.corekit.common.CommonServiceManager
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.theme.activity.AiAdvGameActivity
import org.jxxy.debug.theme.activity.AiDrawActivity
import org.jxxy.debug.theme.activity.AiKnowledgeActivity
import org.jxxy.debug.theme.activity.ChatGameActivity
import org.jxxy.debug.theme.activity.EMODetectorActivity
import org.jxxy.debug.theme.activity.ReadPhotoActivity
import org.jxxy.debug.theme.posemon.PossMonitorActivity

@AutoService(AiService::class)
class AppServiceImpl : AiService{
    override fun goAiPaint(context: Context) {
        context?.startActivity<AiDrawActivity>()
    }
    override fun goAiAdv(context: Context,string: String) {
        val intent = Intent(context,AiAdvGameActivity::class.java)
        intent?.putExtra("string",string)
        context?.startActivity(intent)
    }

    override fun goAiAdv(context: Context) {
        context.startActivity<AiAdvGameActivity>()
    }

    override fun goAiEmo(context: Context) {
        context?.startActivity<EMODetectorActivity>()
    }
    override fun goPose(context: Context) {
        context?.startActivity<PossMonitorActivity>()
    }
    override fun goNavigation(context: Context) {
            CommonServiceManager.service<WebService>()?.gotoWebH5(context, "https://www.toolai.io/zh/category")
    }
    override fun goOCR(context: Context) {
            context.startActivity<ReadPhotoActivity>()
    }
    override fun goKnowledgeBase(context: Context) {
        context.startActivity<AiKnowledgeActivity>()
    }
    override fun goGuessing(context: Context) {
        context.startActivity<ChatGameActivity>()
    }
}