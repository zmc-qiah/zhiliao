package org.jxxy.debug.test.fragment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.test.R
import org.jxxy.debug.test.databinding.ItemAnswerSheetBinding

class AnswerSheetRvAdapter(
    val list: ArrayList<Int>,
    val isGetQuestion: ArrayList<Boolean>,
    val itemState: ArrayList<Int>
) : SingleTypeAdapter<Int>() {

    lateinit var nowChoose: ItemAnswerSheetBinding

    interface OnClickListener {
        fun replace(index: Int)
        fun dismiss()
        fun getQuestion(count: Int)
    }

    companion object {
        const val ANSWER_SHEET_NORMAL = 1
        const val ANSWER_SHEET_TRUE = 2
        const val ANSWER_SHEET_WRONG = 3
        const val ANSWER_SHEET_WORK = 4
    }

    lateinit var onClick: OnClickListener
    var count = 1

    init {
        add(list)
//        if (isGetQuestion.size == 0) {
//            for (i in 0..((list.size - 1) / 10)) {
//                if(i != 0) {
//                    isGetQuestion.add(false)
//                }else{
//                    isGetQuestion.add(true)
//                }
//            }
//        }
    }

    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {
        return AnswerSheetViewHolder(ItemAnswerSheetBinding.inflate(inflater, parent, false))
    }

    inner class AnswerSheetViewHolder(view: ItemAnswerSheetBinding) :
        SingleViewHolder<ItemAnswerSheetBinding, Int>(view) {
        override fun setHolder(entity: Int) {
            view.answerSheetBtn.setBackgroundResource(
                when (itemState[entity - 1]) {
                    ANSWER_SHEET_TRUE -> R.drawable.button_option_true
                    ANSWER_SHEET_WRONG -> R.drawable.button_option_wrong
                    else -> R.drawable.button_option_normal
                }
            )
            view.answerSheetBtn.text = "" + entity
            view.answerSheetBtn.singleClick {
                onClick.replace(entity - 1)
                onClick.dismiss()
            }
            var sum = 0

            Log.d("TTTTTTTTTTTTTTTTTTT", "$entity")
//            if (entity % 10 == 1 && isGetQuestion[(entity - 1) / 10] == false) {
//                var sum = 0
//                onClick.getQuestion((entity - 1)/10)
//                isGetQuestion[(entity - 1) / 10] = true
//                Log.d("TTTTTTTTTTTTTTTT", "我增加题目了")
//            }
        }
    }
}
