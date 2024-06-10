package org.jxxy.debug.theme.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintSet

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.common.util.GlideEngine
import org.jxxy.debug.common.bean.Node
import org.jxxy.debug.common.databinding.FragmentThinkMapMofidyBinding
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.theme.R
import org.jxxy.debug.theme.myListener.EditDialogListener

class EditDialog(val initText:String): BottomSheetDialogFragment() {
    var id: Int? = null
    var listener: EditDialogListener? = null
    var oldHeight = WindowManager.LayoutParams.WRAP_CONTENT
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
        setStyle(STYLE_NORMAL, org.jxxy.debug.common.R.style.AppBottomSheet)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.enjoyIcon.gone()
        binding.aiteIcon.gone()
        binding.photoIcon.gone()
        binding.moreIcon.gone()
        binding.pictureRecyclerView.gone()
        binding.editText.setText(initText)
        binding.submitButton.text = "保存"
        binding.submitButton.singleClick {
            listener?.let {
                listener?.submit(binding.editText.text.toString())
                dismiss()
            }
        }
        binding.fullScreenIcon.tag = false
        binding.fullScreenIcon.singleClick {
            val b = it.tag as Boolean
            if (b) {
                val frameLayout =
                    dialog?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                frameLayout?.let {
                    val params = it.layoutParams
                    oldHeight = params.height
                    params.height = WindowManager.LayoutParams.MATCH_PARENT
                    it.layoutParams = params
                    val behaviour = BottomSheetBehavior.from(it)
                    behaviour.peekHeight = 3500
                    behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                    behaviour.addBottomSheetCallback(object : BottomSheetCallback() {
                        override fun onStateChanged(bottomSheet: View, newState: Int) {
                            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                        }

                        override fun onSlide(bottomSheet: View, slideOffset: Float) {
                        }
                    })
                    val constrainSet = ConstraintSet()
                    constrainSet.clone(binding.root)
                    constrainSet.clear(binding.pictureRecyclerView.id, ConstraintSet.TOP)
                    constrainSet.connect(
                        binding.editText.id,
                        ConstraintSet.BOTTOM,
                        binding.pictureRecyclerView.id,
                        ConstraintSet.TOP,
                        0
                    )
                    constrainSet.applyTo(binding.root)
                    binding.editText.maxLines = Int.MAX_VALUE
                    binding.editText.layoutParams.height = 0
                }
            } else {
                val frameLayout =
                    dialog?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                frameLayout?.let {
                    val params = it.layoutParams
                    params.height = oldHeight
                    it.layoutParams = params
                    val constrainSet = ConstraintSet()
                    constrainSet.clone(binding.root)
                    constrainSet.connect(
                        binding.pictureRecyclerView.id,
                        ConstraintSet.TOP,
                        binding.editText.id,
                        ConstraintSet.BOTTOM,
                        0
                    )
                    constrainSet.applyTo(binding.root)
                    binding.editText.maxLines = 2
                }
            }
            it.tag = !b
        }
        binding.photoIcon.singleClick {
            binding.editText.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        binding.editText.parent.requestDisallowInterceptTouchEvent(true)
                    }

                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        binding.editText.parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
                false
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        return dialog
    }
    override fun onStart() {
        super.onStart()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }
}
