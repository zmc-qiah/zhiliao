package org.jxxy.debug.theme.activity

import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.theme.adapter.ChatAdapter
import org.jxxy.debug.theme.bean.ChatContent
import org.jxxy.debug.theme.bean.ChatConversation
import org.jxxy.debug.theme.bean.StreamData
import org.jxxy.debug.theme.databinding.FragmemtChatDialogBinding
import org.jxxy.debug.theme.dialog.EditDialog
import org.jxxy.debug.theme.http.repository.AiRepository
import org.jxxy.debug.theme.http.viewModel.ThemeViewModel
import org.jxxy.debug.theme.myListener.ChatItemClick
import org.jxxy.debug.theme.myListener.EditDialogListener
import org.jxxy.debug.theme.myListener.StreamResponseListener

class ChatDialogActivity:BaseActivity<FragmemtChatDialogBinding>() {
    private val viewModel: ThemeViewModel by lazy { ViewModelProvider(this).get(ThemeViewModel::class.java) }
    val adapter by lazy{ChatAdapter()}
    val effectNum = 6
    val aiStream : AiRepository by lazy {
        AiRepository()
    }
    val clickListener = object :ChatItemClick{
        override fun onClick(viewType: String, entity: ChatContent,postion:Int) {
            when(viewType){
                ChatContent.user ->{
                    val subList = ArrayList(adapter.list.subList(0, postion))
                    val dialog = EditDialog(entity.content)
                    dialog.listener = object :EditDialogListener{
                        override fun submit(content: String) {
                            subList.add(ChatContent(ChatContent.user,content))
                            adapter.clearAndAdd(subList)
                            if(postion == 0){
                                view.chatRV.smoothScrollToPosition(postion-1)
                            }
                            val list = ArrayList<ChatContent>()
                            for (chat in subList){
                                list.add(chat as ChatContent)
                            }
                            requestChat(list)
//                            viewModel.chat(subList,effectNum)
                            view.submitIcon.gone()
                            view.loadingAnimation.setViewColor(org.jxxy.debug.common.R.color.primary_200)
                            view.loadingAnimation.show()
                            view.loadingAnimation.startAnim()
                        }
                    }
                    dialog.show(supportFragmentManager,dialog.tag)
                }
                ChatContent.assistant ->{
                    val subList = ArrayList(adapter.list.subList(0, postion))
                    adapter.clearAndAdd(subList)
                    view.chatRV.smoothScrollToPosition(postion-1)
                    val list = ArrayList<ChatContent>()
                    for (chat in subList){
                        list.add(chat as ChatContent)
                    }
                    requestChat(list)
//                    viewModel.chat(subList,effectNum)
                    view.submitIcon.gone()
                    view.loadingAnimation.setViewColor(org.jxxy.debug.common.R.color.primary_200)
                    view.loadingAnimation.show()
                    view.loadingAnimation.startAnim()
                }
            }
        }

    }
    override fun bindLayout(): FragmemtChatDialogBinding = FragmemtChatDialogBinding.inflate(layoutInflater)
    override fun initView() {
        adapter.listener = clickListener
        view.chatRV.adapter = adapter
        view.submitIcon.singleClick {
            val string = view.editText.text.toString()
            if (!"".equals(string)&&string.length>0){
                view.editText.setText("")
                view.editText.clearFocus()
                val charContent = ChatContent(ChatContent.user,string)
                adapter.add(charContent)
                val list = ArrayList<ChatContent>()
                for (chat in adapter.list){
                    list.add(chat as ChatContent)
                }
                requestChat(list)
//                viewModel.chat(adapter.list,effectNum)
                view.submitIcon.gone()
                view.loadingAnimation.setViewColor(org.jxxy.debug.common.R.color.primary_200)
                view.loadingAnimation.show()
                view.loadingAnimation.startAnim()
                view.chatRV.smoothScrollToPosition(adapter.itemCount - 1)
            }else{
                "请输入问题描述".toast()
            }
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.root.windowToken, 0)
        }
        val userHead : String = "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/04/785d93dd-5653-4d8e-9814-655abb26a7ba.jpg"
        val assistantHead : String = "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/09/3434bb40-66e7-4a54-a458-faa0c248c936.png"
        ChatContent.userPicture = userHead
        ChatContent.assistantPicture = assistantHead
    }

    override fun subscribeUi() {
        viewModel.answerLiveData.observe(this){
            it.onSuccess {
                it?.let {
                    adapter.add(ChatContent(ChatContent.assistant,it))
                    view.loadingAnimation.stopAnim()
                    view.loadingAnimation.gone()
                    view.submitIcon.show()
                }
            }
            it.onError{ error,res->
                adapter.add(ChatContent(ChatContent.assistant,error?.message.toString()))
                view.loadingAnimation.stopAnim()
                view.loadingAnimation.gone()
                view.submitIcon.show()
            }
        }
        viewModel.userInfoLiveData.observe(this){
            it.onSuccess {
                it?.let {
                    ChatContent.userPicture = it.getOrNull(0)?.headPhoto
                    ChatContent.assistantPicture = it.getOrNull(1)?.headPhoto
                }
            }
        }
        viewModel.getUserInfo()
    }

    fun requestChat(list : ArrayList<ChatContent>){
        lifecycleScope.launch(Dispatchers.Main) {
            val chat = aiStream.chat(ChatConversation(list))
            chat.data?.let {
                val nowString : String = it
                adapter.add(ChatContent(ChatContent.assistant,nowString))
                view.loadingAnimation.stopAnim()
                view.loadingAnimation.gone()
                view.submitIcon.show()
                view.chatRV.smoothScrollToPosition(adapter.itemCount - 1)
            }
        }
    }
}