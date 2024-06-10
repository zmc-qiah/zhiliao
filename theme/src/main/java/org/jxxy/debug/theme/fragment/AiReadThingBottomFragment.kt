package org.jxxy.debug.theme.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.theme.R
import org.jxxy.debug.theme.adapter.ReadAdapter
import org.jxxy.debug.theme.bean.ReadSomethingInnerResult
import org.jxxy.debug.theme.databinding.FragmentReadThingBottomBinding

class AiReadThingBottomFragment(val list : List<ReadSomethingInnerResult>) : BottomSheetDialogFragment() {

    lateinit var way : () -> Unit
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = FragmentReadThingBottomBinding.inflate(layoutInflater)
        val adapter = ReadAdapter()
        adapter.add(list)
        view?.bottomRv?.adapter = adapter
        view?.bottomRv?.layoutManager = LinearLayoutManager(context)
        view?.bottomRv?.addItemDecoration(CommonItemDecoration(10f))
        adapter.notifyItemRangeChanged(0,adapter.itemCount - 1)
        return view?.root
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val bottomSheetDialog = dialog as BottomSheetDialog
        bottomSheetDialog.setOnShowListener { dialogInterface ->
            val bottomSheet = bottomSheetDialog?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.setBackgroundColor(resources.getColor(org.jxxy.debug.corekit.R.color.transparent))
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
            bottomSheetBehavior.peekHeight = 1600
        }
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyBottomSheetDialog)
    }

    //    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val binding = FragmentBottomBinding.inflate(layoutInflater)
//        binding.bottomRv.adapter = AnswerSheetRvAdapter(answerSheetList)
//        binding.bottomRv.layoutManager = GridLayoutManager(this.context,5)
//        binding.bottomRv.addItemDecoration(SpanItemDecoration(15f,10f,5))
//        setStyle(STYLE_NORMAL, R.style.styleBottom)
//        return binding.root
//    }
    override fun onDestroy() {
        way.invoke()
        super.onDestroy()
    }
}