package org.jxxy.debug.thinkMap.fragmemt

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
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.style.TitleBarStyle
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.common.util.GlideEngine
import org.jxxy.debug.thinkMap.MyListener.ModifyDialogListener
import org.jxxy.debug.thinkMap.MyListener.OnItemClickListener
import org.jxxy.debug.thinkMap.R
import org.jxxy.debug.thinkMap.adapter.PicturesAdapter
import org.jxxy.debug.common.bean.Node
import org.jxxy.debug.common.databinding.FragmentThinkMapMofidyBinding
import org.jxxy.debug.common.util.iBridgeViewLifecycle
class ModifyDialog(val node: Node) : BottomSheetDialogFragment() {
    val adapter: PicturesAdapter by lazy {
        PicturesAdapter()
    }
    val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(v: View?, position: Int) {
            PictureSelector.create(requireContext())
                .openPreview()
                .setImageEngine(GlideEngine.createGlideEngine())
                .setAttachViewLifecycle(iBridgeViewLifecycle)
                .startActivityPreview(position, true, adapter.list as java.util.ArrayList<LocalMedia>)
        }
        override fun openPicture() {
        }
    }
    var id: Int? = null
    var listener: ModifyDialogListener? = null
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
        setStyle(STYLE_NORMAL, R.style.AppBottomSheet)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pictureRecyclerView.adapter = adapter
        adapter.listener = onItemClickListener
        binding.editText.setText(node.text)
        node.list = node.pictureList?.map { LocalMedia.generateHttpAsLocalMedia(it) } ?: emptyList()
        if (node.list?.size?:0 > 0) {
            adapter.add(node.list)
            binding.pictureRecyclerView.show()
        }
        binding.submitButton.text = "保存"
        binding.submitButton.singleClick {
            listener?.let {
                it.submit(text = binding.editText.text.toString(), adapter.list)
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
                    constrainSet.connect(binding.editText.id, ConstraintSet.BOTTOM, binding.pictureRecyclerView.id, ConstraintSet.TOP, 0)
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
                    constrainSet.connect(binding.pictureRecyclerView.id, ConstraintSet.TOP, binding.editText.id, ConstraintSet.BOTTOM, 0)
                    constrainSet.applyTo(binding.root)
                    binding.editText.maxLines = 2
                }
            }
            it.tag = !b
        }
        binding.photoIcon.singleClick {
            val whiteTitleBarStyle = TitleBarStyle()
//            whiteTitleBarStyle.titleBackgroundColor =
//                ContextCompat.getColor(requireContext(), R.color.white)
//            whiteTitleBarStyle.titleDrawableRightResource = R.drawable.icon_close
//            whiteTitleBarStyle.titleLeftBackResource = R.drawable.icon_close
//            whiteTitleBarStyle.titleTextColor =
//                ContextCompat.getColor(requireContext(), R.color.black)
//            whiteTitleBarStyle.titleCancelTextColor =
//                ContextCompat.getColor(requireContext(), R.color.black)
//            whiteTitleBarStyle.isDisplayTitleBarLine = true
//
//            val whiteBottomNavBarStyle = BottomNavBarStyle()
//            whiteBottomNavBarStyle.bottomNarBarBackgroundColor = Color.parseColor("#EEEEEE")
//            whiteBottomNavBarStyle.bottomPreviewSelectTextColor =
//                ContextCompat.getColor(requireContext(), R.color.black)
//
//            whiteBottomNavBarStyle.bottomPreviewNormalTextColor =
//                ContextCompat.getColor(requireContext(), R.color.grey_dark)
//            whiteBottomNavBarStyle.bottomPreviewSelectTextColor =
//                ContextCompat.getColor(requireContext(), R.color.primary_100)
//            whiteBottomNavBarStyle.isCompleteCountTips = false
//            whiteBottomNavBarStyle.bottomEditorTextColor =
//                ContextCompat.getColor(requireContext(), R.color.black)
//            whiteBottomNavBarStyle.bottomOriginalTextColor =
//                ContextCompat.getColor(requireContext(), R.color.black)
//
//            val selectMainStyle = SelectMainStyle()
//            selectMainStyle.statusBarColor =
//                ContextCompat.getColor(requireContext(), R.color.white)
//            selectMainStyle.isDarkStatusBarBlack = true
//            selectMainStyle.selectNormalTextColor =
//                ContextCompat.getColor(requireContext(), R.color.bg_300)
//            selectMainStyle.selectTextColor =
//                ContextCompat.getColor(requireContext(), R.color.primary_100)
//            selectMainStyle.previewSelectBackground = R.drawable.icon_close
//            selectMainStyle.selectBackground = R.drawable.icon_close
//            selectMainStyle.setSelectText(R.string.app_name)
//            selectMainStyle.mainListBackgroundColor =
//                ContextCompat.getColor(requireContext(), R.color.white)
//            val selectorStyle = PictureSelectorStyle()
//            selectorStyle.setTitleBarStyle(whiteTitleBarStyle)
//            selectorStyle.setBottomBarStyle(whiteBottomNavBarStyle)
//            selectorStyle.setSelectMainStyle(selectMainStyle)
            PictureSelector.create(this)
                .openGallery(SelectMimeType.TYPE_IMAGE)
                .setImageEngine(GlideEngine.createGlideEngine())
                .setSelectedData(adapter.list)
                .forResult(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: ArrayList<LocalMedia>?) {
                        result?.let {
                                it1 ->
                            if (result.size > adapter.list.size) {
                                result.removeAll(adapter.list)
                                adapter.add(result)
                                binding.pictureRecyclerView.show()
                            }
                        }
                    }
                    override fun onCancel() {
                    }
                })
        }
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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
    override fun onStart() {
        super.onStart()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }
}
