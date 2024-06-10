import android.content.Context // ktlint-disable filename

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.common.scheme.Scheme.Companion.AI
import org.jxxy.debug.common.scheme.Scheme.Companion.GAME
import org.jxxy.debug.common.scheme.Scheme.Companion.H5
import org.jxxy.debug.common.scheme.SchemeAI
import org.jxxy.debug.common.scheme.SchemeGame
import org.jxxy.debug.common.service.*
import org.jxxy.debug.corekit.common.CommonServiceManager

fun Scheme.navigation(context: Context) {
    when (this.type) {
        H5-> {
            this.url?.let {
                CommonServiceManager.service<WebService>()?.gotoWebH5(context, it)
            }
        }
        Scheme.SEARCH -> {
            this.keyword?.let {
                goSearch(context,it)
            }
        }
        Scheme.DETAIL -> {
            this.resourceId?.let {
                goResource(context,it)
            }
        }
        Scheme.Society->{
            when(this.societyType){
                1-> goDiscuss(context)
                2-> goExcellentClassroom(context)
                3-> goExperience(context)
                4-> goHistory(context)
                5-> goLecture(context)
                6-> goMovable(context)
                7-> goPlay(context)
                8-> goPractice(context)
                9-> goRecommend(context)

            }
        }
        Scheme.Plan->{
            CommonServiceManager.service<MemberService>()?.goPlan(context)
        }
        Scheme.PAINT->{
            CommonServiceManager.service<AiService>()?.goAiPaint(context)
        }
        Scheme.Text->{
            CommonServiceManager.service<AppService>()?.goMainActivity(context,2)
        }
        GAME ->{
            this as SchemeGame
            CommonServiceManager.service<AiService>()?.goAiAdv(context,this.content)
        }
        AI ->{
            (this as? SchemeAI)?.let {
                when(it.aiType){
                    SchemeAI.EMO ->{
                        CommonServiceManager.service<AiService>()?.goAiEmo(context)
                    }
                    SchemeAI.POSE ->{
                        CommonServiceManager.service<AiService>()?.goPose(context)
                    }
                    SchemeAI.Guessing ->{
                        CommonServiceManager.service<AiService>()?.goGuessing(context)
                    }
                    SchemeAI.ADV ->{
                        CommonServiceManager.service<AiService>()?.goAiAdv(context)
                    }
                    SchemeAI.PAINT ->{
                        CommonServiceManager.service<AiService>()?.goAiPaint(context)
                    }
                    SchemeAI.KNOWLEDGE ->{
                        CommonServiceManager.service<AiService>()?.goKnowledgeBase(context)
                    }
                    SchemeAI.NAVIGATION ->{
                        CommonServiceManager.service<AiService>()?.goNavigation(context)
                    }
                    SchemeAI.OCR ->{
                        CommonServiceManager.service<AiService>()?.goOCR(context)
                    }
                    else -> {}
                }
            }
        }
        Scheme.EMO ->{
            CommonServiceManager.service<AiService>()?.goOCR(context)
        }
    }
}
