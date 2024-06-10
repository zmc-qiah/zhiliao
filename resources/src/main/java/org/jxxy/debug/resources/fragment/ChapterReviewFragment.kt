package org.jxxy.debug.resources.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.resources.R
import org.jxxy.debug.resources.adapter.ChapterCommentAdapter
import org.jxxy.debug.resources.bean.ChapterCommentBody
import org.jxxy.debug.resources.bean.ItemCommentedChapter
import org.jxxy.debug.resources.databinding.FragmentChapterReviewBinding
import org.jxxy.debug.resources.http.viewModel.ResourceViewModel
import org.jxxy.debug.resources.myListener.SubmitListener

class ChapterReviewFragment(val icc:ItemCommentedChapter, val listener:SubmitListener?=null,
                            var flag:Boolean =false):BottomSheetDialogFragment() {
   private lateinit var binding: FragmentChapterReviewBinding
   val adapter  by lazy { ChapterCommentAdapter() }
    val viewModel: ResourceViewModel by lazy {
        ViewModelProvider(this).get(ResourceViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChapterReviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val frameLayout =
            dialog?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        frameLayout?.let {
            val params = it.layoutParams
            params.height = WindowManager.LayoutParams.MATCH_PARENT
            it.layoutParams = params
            val behaviour = BottomSheetBehavior.from(it)
            behaviour.peekHeight = 0
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            behaviour.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED){
                        behaviour.state = BottomSheetBehavior.STATE_HIDDEN
                    }
                }
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }
            })
        }
//        binding.submitButton.singleClick {
//                val string = binding.editText.text.toString()
//                if (string.length == 0){
//                    "请输入章评".toast()
//                }else{
//                    binding.editText.setText("")
//                    if (flag){
//                        listener?.onSubmit(string)
//                        flag = false
//                    }else{
//                        if (icc.id!= 0) viewModel.addChapterComment(ChapterCommentBody(string,icc.id))
//                        context?.let {
//                        val inputMethodManager = it.getSystemService(androidx.lifecycle.LifecycleService.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
//                        inputMethodManager.hideSoftInputFromWindow(binding.editText.windowToken, 0)
//                    }
//                }
//            }
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
        dialog?.window?.attributes = attributes?.apply {
            dimAmount = 0.0f
        }
        dialog?.window?.setBackgroundDrawableResource(org.jxxy.debug.corekit.R.color.transparent)
        dialog?.window?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)?.setBackgroundResource(org.jxxy.debug.corekit.R.color.transparent)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
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