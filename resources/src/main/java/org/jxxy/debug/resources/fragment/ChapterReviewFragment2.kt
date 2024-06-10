package org.jxxy.debug.resources.fragment

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import org.jxxy.debug.common.util.getHeight
import org.jxxy.debug.common.util.getWidth
import org.jxxy.debug.corekit.common.BaseDialog
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.resources.adapter.ChapterCommentAdapter
import org.jxxy.debug.resources.bean.ChapterCommentBody
import org.jxxy.debug.resources.bean.ItemCommentedChapter
import org.jxxy.debug.resources.databinding.FragmentChapterReviewBinding
import org.jxxy.debug.resources.dialog.CRCD
import org.jxxy.debug.resources.http.viewModel.ResourceViewModel
import org.jxxy.debug.resources.myListener.SubmitListener

class ChapterReviewFragment2(
    val icc: ItemCommentedChapter, val listener: SubmitListener?=null,
    var flag:Boolean =false
):BaseDialog<FragmentChapterReviewBinding>() {
    val adapter  by lazy { ChapterCommentAdapter() }
    val viewModel: ResourceViewModel by lazy {
        ViewModelProvider(this).get(ResourceViewModel::class.java)
    }
    init {
        gravity = Gravity.BOTTOM
        alpha = 0.3f
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.MATCH_PARENT
        enableBack = true
    }
    override fun getView(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentChapterReviewBinding = FragmentChapterReviewBinding.inflate(layoutInflater,parent,false)

    override fun initView(view: FragmentChapterReviewBinding) {
        val binding = view
        binding.editText.hint = "写想法"
        binding.submitButton.singleClick {
            val string = binding.editText.text.toString()
            if (string.length == 0){
                "请输入章评".toast()
            }else{
                binding.editText.setText("")
                if (flag){
                    listener?.onSubmit(string)
                    flag = false
                }else{
                    if (icc.id!= 0) viewModel.addChapterComment(ChapterCommentBody(string,icc.id))
                    context?.let {
                        val inputMethodManager = it.getSystemService(androidx.lifecycle.LifecycleService.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(binding.editText.windowToken, 0)
                    }
                }
            }
        }
//        binding.editText.singleClick {
//           CRCD(
//                icc
//            ){
//                view.editText.text = it
//                if (flag){
//                    listener?.onSubmit(it)
//                    flag = false
//                }else{
//                    if (icc.id!= 0) viewModel.addChapterComment(ChapterCommentBody(it,icc.id))
//                    context?.let {
//                        val inputMethodManager = it.getSystemService(androidx.lifecycle.LifecycleService.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
//                        inputMethodManager.hideSoftInputFromWindow(binding.editText.windowToken, 0)
//                    }
//                }
//            }.show(
//                parentFragmentManager
//            )
//        }
        binding.reviewRV.adapter = adapter
        binding.reviewRV.addItemDecoration(CommonItemDecoration(12f))
        adapter.add(icc)
        viewModel.chapterLiveData.observe(this){
            it.onSuccess {
                it?.commentInfos?.let {
                    val a = adapter.itemCount
                    if (adapter.addNot(it)) binding.reviewRV.smoothScrollToPosition(a)
                }
            }
        }
        viewModel.getChapterComment(icc.id)
    }
    override fun onStart() {
        super.onStart()
        val attributes = dialog?.window?.attributes
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,org.jxxy.debug.common.R.style.AppBottomSheet)
    }
    fun refresh(id:Int){
        icc.id = id
        if (id == 0){
            Log.d("TAG", "refresh: aaaaaa")
            "error".toast()
        }
        viewModel.getChapterComment(icc.id)
    }
}