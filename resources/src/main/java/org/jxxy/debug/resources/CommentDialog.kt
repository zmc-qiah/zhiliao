package org.jxxy.debug.resources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jxxy.debug.common.service.loginCheck
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.resources.videoHelper.MyHelperCommentResponse
import org.jxxy.debug.resources.videoHelper.MyHelperView
import org.jxxy.debug.resources.videoHelper.MyVideoPlayerHelperRotate

class CommentDialog():NoteDialog() {
    constructor(id:Int):this(){
        this.id = id
    }
//    @JvmOverloads constructor(id: Int?, buttonHelper: MyHelperView, editHelper: MyHelperView): this(){
//        this.id=id
//        this.buttonHelper=buttonHelper
//        this.editHelper = editHelper
//    }
    constructor(id: Int?,buttonHelper:MyHelperView,): this(){
    this.id=id
    this.buttonHelper=buttonHelper
    }
    constructor(id: Int?,myHelperCommentResponse:MyHelperCommentResponse,): this(){
        this.id=id
        this.myHelperCommentResponse=myHelperCommentResponse
    }
    var myHelperCommentResponse:MyHelperCommentResponse?=null

    override fun loadView() {
        super.loadView()
        binding.submitButton.singleClick {
            context?.let { it1 -> loginCheck(it1){
                id?.let { it2 ->
                    viewModel.addComment(it2.toInt(),binding.editText.text.toString())
                } }
            }
        }
    }

    override fun subscribeUi() {
        super.subscribeUi()
        viewModel.addCommentLiveData.observe(this){
            it.onSuccess {
            it?.let {res->
                myHelperCommentResponse?.help(res)
                "发布成功".toast()
                dismiss()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.editText.requestFocus()
    }
}