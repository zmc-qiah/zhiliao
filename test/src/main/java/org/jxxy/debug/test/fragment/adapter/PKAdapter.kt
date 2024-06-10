package org.jxxy.debug.test.fragment.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.common.widget.read.view.BookLayoutManager
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.test.R
import org.jxxy.debug.test.databinding.ItemPkChooseBinding
import org.jxxy.debug.test.fragment.bean.PkChoose
import org.jxxy.debug.test.fragment.myListener.ItemClickListener

class PKAdapter(var listener: ItemClickListener? =null) : SingleTypeAdapter<PkChoose>() {
    var trueAnswer: String = ""
    var nowChoose: Int = -1
    var options: ArrayList<ItemPkChooseBinding> = ArrayList()
    var isTime: Boolean = true
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {
        return PkQuestionViewHolder(ItemPkChooseBinding.inflate(inflater, parent, false),listener)
    }

    inner class PkQuestionViewHolder(view: ItemPkChooseBinding,val listener: ItemClickListener? =null) :
        SingleViewHolder<ItemPkChooseBinding, PkChoose>(view) {
        override fun setHolder(entity: PkChoose) {
            view.pkChooseTv.text = entity.content
            options.add(view)
            Log.d("optionSize","${options.size}")
            view.firstPlayerChooseTv.hide()
            view.secondPlayerChooseTv.hide()
            view.root.singleClick {
                listener?.onItemClick(it,absoluteAdapterPosition,entity)
            }
        }

        override fun setHolder(entity: PkChoose, payload: Any) {
        }
    }

    fun turnToString(id: Int): String {
        return (id + 'A'.toInt()).toChar().toString()
    }

    fun checkAnswer(choose: Int): Boolean {
        return turnToString(choose).equals(trueAnswer)
    }

    fun allIsEnabledFalse() {
        for (view in options) {
            view.root.isEnabled = false
        }
    }

    fun changeState() {

    }

    fun show(choose: Int, who: Int) {
        val view = options[choose]
        val b = checkAnswer(choose)
        if (b &&who == 0) {
            view.pkChooseTv.setBackgroundResource(R.drawable.button_pk_true)
            view.pkChooseTv.setTextColor(Color.parseColor("#FFFFFF"))
        } else if(who == 0){
            val trueAnswerInt = trueAnswer.toCharArray()[0].toInt() - 'A'.toInt()
            view.pkChooseTv.setBackgroundResource(R.drawable.button_pk_wrong)
            view.pkChooseTv.setTextColor(Color.parseColor("#FFFFFF"))
            options[trueAnswerInt].pkChooseTv
            options[trueAnswerInt].pkChooseTv.setBackgroundResource(R.drawable.button_pk_true)
            options[trueAnswerInt].pkChooseTv.setTextColor(Color.parseColor("#FFFFFF"))
        }
        when (who) {
            0 -> {
                view.firstPlayerChooseTv.text = "你"
                view.firstPlayerChooseTv.show()
            }
            1 -> {
                view.secondPlayerChooseTv.text = "TA"
                view.secondPlayerChooseTv.show()
            }
        }
    }
    class answerState(){
        var isTrue = false
        var who = 0
        constructor(isTrue: Boolean,who:Int) : this(){
            this.isTrue = isTrue
            this.who = who
        }
    }


}