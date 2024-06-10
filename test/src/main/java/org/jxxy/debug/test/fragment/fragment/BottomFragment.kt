package org.jxxy.debug.test.fragment.fragment

import android.app.Dialog
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
import org.jxxy.debug.test.databinding.FragmentBottomBinding
import org.jxxy.debug.test.fragment.adapter.AnswerSheetRvAdapter

class BottomFragment(val isGetQuestion : ArrayList<Boolean>,val itemState:ArrayList<Int>,val answerSheetList : ArrayList<Int>,sum:Int) : BottomSheetDialogFragment() {
    lateinit var onClick : AnswerSheetRvAdapter.OnClickListener
    init {
        for (i in 1..sum){
            answerSheetList.add(i)
            itemState.add(AnswerSheetRvAdapter.ANSWER_SHEET_NORMAL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = FragmentBottomBinding.inflate(layoutInflater)
        val adapter = AnswerSheetRvAdapter(answerSheetList,isGetQuestion,itemState)
        adapter?.onClick = onClick
        view?.bottomRv?.adapter = adapter
        view?.bottomRv?.layoutManager = GridLayoutManager(this.context,5)
        view?.bottomRv?.addItemDecoration(SpanItemDecoration(15f,10f,5))
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

}