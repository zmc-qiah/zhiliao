package org.jxxy.debug.test.fragment.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.test.R
import org.jxxy.debug.test.databinding.FragmentBottomDialogInterVideoBinding
import org.jxxy.debug.test.fragment.adapter.InterVideoChooseAdapter
import org.jxxy.debug.test.fragment.bean.InterVideoChoose

class MyBottomDialogFragment(val list: ArrayList<InterVideoChoose>) : BottomSheetDialogFragment() {
    lateinit var listener : InterVideoChooseAdapter.OnItemClickListener
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = FragmentBottomDialogInterVideoBinding.inflate(inflater, container, false)
        val adapter = InterVideoChooseAdapter()
        adapter.add(list)
        adapter.setOnItemClickListener(listener)
        view.interVideoRl.adapter = adapter
        view.interVideoRl.layoutManager = GridLayoutManager(context, 2)
        view.interVideoRl.addItemDecoration(SpanItemDecoration(10f, 10f, 2))

        return view.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStyle(STYLE_NORMAL, R.style.bottomSheetStyleWrapper)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(false)
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


}
