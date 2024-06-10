package org.jxxy.debug.test.fragment.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.jxxy.debug.test.R
import org.jxxy.debug.test.databinding.FragmentBottomBinding

class QuestionBottomDialogFragment : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = FragmentBottomBinding.inflate(inflater,container,false)
        view.bottomRv.adapter

        return view.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val bottomSheetDialog = dialog as BottomSheetDialog
        bottomSheetDialog.setOnShowListener { dialogInterface ->
            val bottomSheet = bottomSheetDialog?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.setBackgroundColor(resources.getColor(org.jxxy.debug.corekit.R.color.transparent))
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
            bottomSheetBehavior.isHideable = false
            bottomSheetBehavior.peekHeight = 1600
        }
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.bottomSheetStyleWrapper)
    }
}