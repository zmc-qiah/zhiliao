package org.jxxy.debug.thinkMap.fragmemt

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.jxxy.debug.common.databinding.FragmentThinkMapMofidyBinding
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.thinkMap.MyListener.ModifyDialogListener
import org.jxxy.debug.thinkMap.R

class QuoteDialog: BottomSheetDialogFragment() {
    var listener: ModifyDialogListener? = null
    lateinit var binding: FragmentThinkMapMofidyBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThinkMapMofidyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheet)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.photoIcon.gone()
        binding.moreIcon.gone()
        binding.aiteIcon.gone()
        binding.fullScreenIcon.gone()
        binding.enjoyIcon.gone()
        binding.editText.hint = "请输入目标资源索引"
        binding.editText.inputType = InputType.TYPE_CLASS_NUMBER
        binding.submitButton.text = "应用"
        binding.submitButton.singleClick {
            val toString = binding.editText.text.toString()
            if(toString.length == 0){
                "请输入内容".toast()
            }else{
                binding.editText.setText("")
                listener?.submit(toString, emptyList())
                dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }
}
