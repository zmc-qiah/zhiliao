package org.jxxy.debug.test.fragment.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.test.databinding.ItemCollectionBinding
import org.jxxy.debug.test.fragment.activity.QuestionActivity
import org.jxxy.debug.test.fragment.bean.CollectionOrMistakeQuestion
import org.jxxy.debug.test.fragment.viewModel.QuestionViewModel


class CollectionRvAdapter(val type:Int, val viewModel: QuestionViewModel? = null) : SingleTypeAdapter<CollectionOrMistakeQuestion>() {
    init {

    }
    companion object{
        const val COLLECTION = 1
        const val WRONG = 2
        const val SPECIAL = 3
        const val ANSWER = 4
    }
    inner class AnswerRvViewHolder(view : ItemCollectionBinding) : SingleViewHolder<ItemCollectionBinding,CollectionOrMistakeQuestion>(view){
        override fun setHolder(entity: CollectionOrMistakeQuestion) {
            if (entity.questionText.length >= 40) {
                view.questionContentsTv.text = entity.questionText.substring(0, 40) + "..."
            }else{
                view.questionContentsTv.text = entity.questionText
            }
            view.checkTv.singleClick {
                val intent = Intent(this.context,QuestionActivity::class.java)
                intent.putExtra("type",type)
                intent.putExtra("id",entity.questionId)
                context.startActivity(intent)
            }
            view.deleteTv.singleClick {
                val list = ArrayList<CollectionOrMistakeQuestion>()
                list.addAll(data)
                when(type){
                    WRONG ->{
                        viewModel?.deleteMistake(list[position].questionId)
                    }
                    COLLECTION->{
                        viewModel?.deleteCollection(list[position].questionId)
                    }
                }
                list.removeAt(position)
                clearAndAdd(list)
                notifyItemMoved(0,list.size)
            }
//            view.typeTv.text = entity.type
//            view.answerSumTv.text = "总计${entity.sum.toString()}题"
//            view.wayTv.text = when(entity.type){
//                "单选" -> "选出一个正确选项"
//                "多选" -> "选择至少一个正确的选项"
//                "判断" -> "判断下面题目正误"
//                else ->"没有这类题型"
//            }
            //测试用
//            val id = when(entity.type){
//                "单选" -> 1
//                "多选" -> 2
//                "判断" -> 3
//                else ->"没有这类题型"
//            }
//            view.answerTypeTv.text = when(type){
//                COLLECTION -> "收藏"
//                WRONG -> "错题"
//                TEST -> "测试"
//                ANSWER -> "答题"
//                else -> null
//            }
//            view.toAnswerBtn.singleClick {
//                context?.run {
//                    val intent = Intent(context, QuestionActivity::class.java)
//                    intent.putExtra("id",id)
//                    startActivity(intent)
//                }
//            }
        }
    }

    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {
        return AnswerRvViewHolder(ItemCollectionBinding.inflate(inflater,parent,false))
    }
}