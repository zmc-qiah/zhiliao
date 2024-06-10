package org.jxxy.debug.theme.activity

import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.lifecycle.ViewModelProvider
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.theme.bean.ChatContent
import org.jxxy.debug.theme.databinding.ActivityChatGameBinding
import org.jxxy.debug.theme.dialog.ChatGameDialog
import org.jxxy.debug.theme.http.viewModel.ThemeViewModel

class ChatGameActivity : BaseActivity<ActivityChatGameBinding>() {
    val viewModel: ThemeViewModel by lazy {
        ViewModelProvider(this).get(ThemeViewModel::class.java)
    }
    val list : ArrayList<ChatContent> = ArrayList()
    val settingUer : String = "我们正在玩猜人游戏，我心中会想一个人，他是个公众人物，你可以问我一些问题，一个个问，我只会回答是或否，如果你猜出，就说:我猜是xxx,我猜对了吗"
    val settingAi : String = "好的让我们开始吧，请问这个人物是男的吗"
    val num : Int = 40
    val title : String = "猜人游戏"
    val rule : String = "游戏规则:你可以在心中想一个你所知道的人，当你想好后，ai会向你提出各种关于这个人的问题，你需要根据他的问题回答是与否，在回答足够多的问题后，ai会告诉你他的猜测，看看他是否能猜中你心中的人"
    lateinit var ruleDialog: ChatGameDialog
    override fun bindLayout(): ActivityChatGameBinding {
        return ActivityChatGameBinding.inflate(layoutInflater)
    }

    override fun initView() {
        view.chatGameAiTv.text = settingAi
        val fadeOutAnimationYes = AlphaAnimation(1f, 0f)
        fadeOutAnimationYes.duration = 2000
        fadeOutAnimationYes.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                view.chatGameYesTv.hide()
                view.chatGameYesInTv.hide()
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
        val fadeOutAnimationNo = AlphaAnimation(1f, 0f)
        fadeOutAnimationNo.duration = 2000
        fadeOutAnimationNo.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                view.chatGameNoTv.hide()
                view.chatGameNoInTv.hide()
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
        view.chatGameBackGroundIv.load("https://tse3-mm.cn.bing.net/th/id/OIP-C.eGTYGtXIlB5MBwXQDPY5IwHaLH?w=120&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7")
        view.chatGameAiIv.load("https://tse1-mm.cn.bing.net/th/id/OIP-C.TYoDY6CGmwhYP7_reOnMJgAAAA?w=202&h=138&c=7&r=0&o=5&dpr=1.3&pid=1.7",8)
        view.chatGameYesTv.singleClick {
            view.chatGameYesTv.startAnimation(fadeOutAnimationYes)
            view.chatGameYesInTv.startAnimation(fadeOutAnimationYes)
            list.add(ChatContent(ChatContent.user,"是"))
            viewModel.chat(list,num)
            turnAllButtonToIsEnabled(false)
        }
        view.chatGameNoTv.singleClick {
            view.chatGameNoTv.startAnimation(fadeOutAnimationNo)
            view.chatGameNoInTv.startAnimation(fadeOutAnimationNo)
            list.add(ChatContent(ChatContent.user,"否"))
            viewModel.chat(list,num)
            turnAllButtonToIsEnabled(false)
        }
//        view.ruleShowIcon.singleClick {
//            ruleDialog.show(supportFragmentManager)
//        }
    }

    override fun subscribeUi() {
        viewModel.answerLiveData.observe(this){
            it.onSuccess {
                list.add(ChatContent(ChatContent.assistant,it!!))
                view.chatGameAiTv.text = it
                view.chatGameYesTv.show()
                view.chatGameYesInTv.show()
                view.chatGameNoTv.show()
                view.chatGameNoTv.show()
                turnAllButtonToIsEnabled(true)
            }
        }
        list.add(ChatContent(ChatContent.user,settingUer))
        list.add(ChatContent(ChatContent.assistant,settingAi))
        ruleDialog = ChatGameDialog(title,rule)
        ruleDialog.show(supportFragmentManager)
    }

    fun turnAllButtonToIsEnabled(flag:Boolean){
        view.chatGameYesTv.isEnabled = flag
        view.chatGameNoTv.isEnabled = flag
    }
}