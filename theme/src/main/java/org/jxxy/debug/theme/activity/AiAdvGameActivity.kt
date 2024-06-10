package org.jxxy.debug.theme.activity

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.jxxy.debug.common.util.BookMarkUtil
import org.jxxy.debug.common.util.close
import org.jxxy.debug.common.util.start
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.theme.adapter.AiAdvGameAdapter
import org.jxxy.debug.theme.bean.ChatContent
import org.jxxy.debug.theme.bean.ChatConversation
import org.jxxy.debug.theme.bean.StreamData
import org.jxxy.debug.theme.databinding.ActivityAiAdvGameBinding
import org.jxxy.debug.theme.dialog.ChatGameDialog
import org.jxxy.debug.theme.http.repository.AiRepository
import org.jxxy.debug.theme.http.viewModel.AiDrawViewModel
import org.jxxy.debug.theme.http.viewModel.ThemeViewModel
import org.jxxy.debug.theme.myListener.StreamResponseListener

class AiAdvGameActivity : BaseActivity<ActivityAiAdvGameBinding>() {
    lateinit var adapter: AiAdvGameAdapter
    val list : ArrayList<ChatContent> = ArrayList()
    val maxNum : Int = 40
    var picId = ""
    val firstBackgroundPic : String = "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/05/572db6aa-72c0-4231-94c2-3d1affdff9e3.jpg"
    var settingString = "让我们结束上一次游戏，开始下一次游戏,你可以跟我玩文字冒险类游戏吗，现在我扮演一个勇士，而你是旁白，你会描述当前我所在的场景，并且编造故事，然后根据故事背景给予我4个选项A,B,C,D,我会回答我的选择，我的血量是100点,你可以根据选项增加或者扣除我的血量，然后你在20句话内给故事设定一个结局，如果我的血量变为0或者达到故事的结局则游戏结束,现在,让我们重新开始游戏"
    var nowString : String = ""
    val title : String = "冒险游戏"
    val rule : String = "游戏规则:在冒险游戏中，你将扮演故事中的主角，在特定的背景下，你需要选择ai所给选项中你认为最好的一项，防止血量被清空，尝试保持良好的血量，并且走向真正的结局"
//    var ai1 = "当然可以！我很愿意参与文字冒险游戏。我会充当旁白，遵守你的规则。请告诉我游戏的背景故事或设置，然后我们可以开始冒险！"
//    var settingString2 = "你会描述当前我所在的场景，并且编造故事，然后每次根据故事背景给予我A、B、C、D，4个选项,请在20句话内给出结局，现在游戏开始"
//    var settingString3 = "现在我们开始游戏"
    lateinit var timer: CountDownTimer
    lateinit var ruleDialog: ChatGameDialog
    val aiStream : AiRepository by lazy {
        AiRepository()
    }
    val aiDrawViewModel : AiDrawViewModel by lazy {
        ViewModelProvider(this).get(AiDrawViewModel::class.java)
    }
    val aiChatViewModel : ThemeViewModel by lazy {
        ViewModelProvider(this).get(ThemeViewModel::class.java)
    }
    override fun bindLayout(): ActivityAiAdvGameBinding {
        return ActivityAiAdvGameBinding.inflate(layoutInflater)
    }

    override fun initView() {
        view.aiAdvGameToolbar.post {
            val positions = IntArray(2)
            view.aiAdvGameToolbar.getLocationOnScreen(positions)
            BookMarkUtil.addView(this, positions[1] + view.aiAdvGameToolbar.height)
            BookMarkUtil.get(3,true)
        }
        val settingsString = intent.getStringExtra("string")
        if(!settingsString.isNullOrEmpty()){
            settingString = settingString + settingsString.substring(0,20) + "以这个故事为背景开始我们的游戏"
        }
        view.aiAdvGameIv.load(firstBackgroundPic,2)
        adapter = AiAdvGameAdapter()
        view.aiAdvGameRv.adapter = adapter
        view.aiAdvGameRv.layoutManager = LinearLayoutManager(this)
        view.aiAdvGameRv.addItemDecoration(CommonItemDecoration(10f))
        list.add(ChatContent(ChatContent.user,settingString))
//        list.add(ChatContent(ChatContent.assistant,"好的"))
//        list.add(ChatContent(ChatContent.user,settingString2))
//        list.add(ChatContent(ChatContent.assistant,"好的"))
//        list.add(ChatContent(ChatContent.user,settingString3))

        view.aiAdvGameOptionATv.singleClick {
            list.add(ChatContent(ChatContent.user,"A"))
            adapter.add(ChatContent(ChatContent.user,"A"))
            view.aiAdvGameRv.smoothScrollToPosition(adapter.itemCount - 1)
            view.aiAdvGameLoading.start()
            setAllViewIsEnabled(false)
//            aiChatViewModel.chat(list, maxNum)
            nowString =""
            requestChat()
        }
        view.aiAdvGameOptionBTv.singleClick {
            list.add(ChatContent(ChatContent.user,"B"))
            adapter.add(ChatContent(ChatContent.user,"B"))
            view.aiAdvGameRv.smoothScrollToPosition(adapter.itemCount - 1)
            view.aiAdvGameLoading.start()
            setAllViewIsEnabled(false)
            //            aiChatViewModel.chat(list, maxNum)
            nowString =""
            requestChat()
        }
        view.aiAdvGameOptionCTv.singleClick {
            list.add(ChatContent(ChatContent.user,"C"))
            adapter.add(ChatContent(ChatContent.user,"C"))
            view.aiAdvGameRv.smoothScrollToPosition(adapter.itemCount - 1)
            view.aiAdvGameLoading.start()
            setAllViewIsEnabled(false)
            //            aiChatViewModel.chat(list, maxNum)
            nowString =""
            requestChat()
        }
        view.aiAdvGameOptionDTv.singleClick {
            list.add(ChatContent(ChatContent.user,"D"))
            adapter.add(ChatContent(ChatContent.user,"D"))
            view.aiAdvGameRv.smoothScrollToPosition(adapter.itemCount - 1)
            view.aiAdvGameLoading.start()
            setAllViewIsEnabled(false)
            //            aiChatViewModel.chat(list, maxNum)
            nowString =""
            requestChat()
        }
    }

    override fun subscribeUi() {
        aiChatViewModel.answerLiveData.observe(this){
            it.onSuccess {
                Log.d("AiChatRes","$it")
                list.add(ChatContent(ChatContent.assistant,it!!))
                adapter.add(ChatContent(ChatContent.assistant,it!!))
                view.aiAdvGameRv.smoothScrollToPosition(adapter.itemCount - 1)
                Log.d("AiListIn","$list")
                aiDrawViewModel.getPictureId(it!!)
            }
        }
        view.aiAdvGameLoading.start()
        lifecycleScope.launch {
            aiStream.chat(ChatConversation(list)).data?.let {
                nowString = it
                    list.add(ChatContent(ChatContent.assistant,nowString))
                adapter.add(ChatContent(ChatContent.assistant,nowString))
                view.aiAdvGameRv.smoothScrollToPosition(adapter.itemCount - 1)
                aiDrawViewModel.getPictureId(nowString)
            }
        }


        aiDrawViewModel.getPictureIdLiveData.observe(this){
            it.onSuccess {
                Log.d("AiDrawRes","${it?.id}")
                picId = it!!.id!!
                initTimer(it!!.estimate!!.toInt() + 5)
            }
        }

        aiDrawViewModel.getPictureByIdLiveData.observe(this){
            it.onSuccess {
                Log.d("AiDrawGet","${it?.gen_img}")
                view.aiAdvGameIv.load(it?.gen_img)
                view.aiAdvGameLoading.close()
                setAllViewIsEnabled(true)
            }
        }
        Log.d("AiList","$list")
//        aiChatViewModel.chat(list,maxNum)
//        view.aiAdvGameLoading.start()
        ruleDialog = ChatGameDialog(title,rule)
        ruleDialog.show(supportFragmentManager)
    }

    fun initTimer(time: Int) {
        timer = object : CountDownTimer((time * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                aiDrawViewModel.getPictureById(picId)
            }
        }.start()
    }

    fun addText(text: String) {
        view.testTv.text = view.testTv.text.toString() + text
    }

    fun setAllViewIsEnabled(flag: Boolean) {
        view.aiAdvGameOptionATv.isEnabled = flag
        view.aiAdvGameOptionBTv.isEnabled = flag
        view.aiAdvGameOptionCTv.isEnabled = flag
        view.aiAdvGameOptionDTv.isEnabled = flag
    }

    fun requestChat(){
        lifecycleScope.launch {
            aiStream.chat(ChatConversation(list)).data?.let {
                nowString = it
                list.add(ChatContent(ChatContent.assistant,nowString))
                adapter.add(ChatContent(ChatContent.assistant,nowString))
                view.aiAdvGameRv.smoothScrollToPosition(adapter.itemCount - 1)
                aiDrawViewModel.getPictureId(nowString)
            }
        }
    }

    override fun onDestroy() {
        BookMarkUtil.removeView()
        super.onDestroy()
    }
}