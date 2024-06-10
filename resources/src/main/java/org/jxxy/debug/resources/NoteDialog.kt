package org.jxxy.debug.resources

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.jxxy.debug.common.service.loginCheck
import org.jxxy.debug.corekit.util.nullOrNot
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.resources.databinding.FragmentVideoNoteBinding
import org.jxxy.debug.resources.http.viewModel.ResourceViewModel
import org.jxxy.debug.resources.videoHelper.MyHelperView
import org.jxxy.debug.resources.videoHelper.MyVideoPlayerHelperRotate

open class NoteDialog(): BottomSheetDialogFragment() {
    val viewModel: ResourceViewModel by lazy {
        ViewModelProvider(this).get(ResourceViewModel::class.java)
    }
    constructor(id:Int?):this(){
        this.id = id
    }
//    @JvmOverloads constructor(id: Int?,buttonHelper:MyHelperView,editHelper:MyHelperView): this(){
//        this.id=id
//        this.buttonHelper=buttonHelper
//        this.editHelper = editHelper
//    }
    constructor(id: Int?,buttonHelper:MyHelperView,): this(){
        this.id=id
        this.buttonHelper=buttonHelper
    }

    var id:Int?=null
    var helper:MyVideoPlayerHelperRotate ?= null
    var buttonHelper:MyHelperView ?= null
    var editHelper:MyHelperView ?= null
    lateinit var binding: FragmentVideoNoteBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,org.jxxy.debug.common.R.style.AppBottomSheet)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonHelper.nullOrNot({
            binding.submitButton.singleClick {
                context?.let{ it1 ->
                    loginCheck(it1){
                        id?.let { it2 ->
                            viewModel.addNote(it2,binding.editText.text.toString())
                            "添加笔记成功".toast()
                            dismiss()
                            binding.editText.setText("")
                        }
                    }
                }
            }
        },{
            it1->
            binding.submitButton.singleClick {
               it1.helper(it)
            }
        })
        editHelper.nullOrNot({
        },{ it1->
            binding.editText.singleClick {
                it1.helper(it)
            }
        })
        binding.editText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
              s?.let {s->
                  binding.submitButton.isEnabled = s.length>=1
              }
            }
            override fun afterTextChanged(s: Editable?) {
            }

        })
        binding.submitButton.isEnabled = false
        helper?.rotate()
        loadView()
        subscribeUi()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }
    open fun subscribeUi(){
    }
    open fun loadView(){}
}