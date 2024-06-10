package org.jxxy.debug.test.fragment.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.test.databinding.ItemInterVideoChooseBinding
import org.jxxy.debug.test.fragment.activity.InterVideoActivity
import org.jxxy.debug.test.fragment.bean.InterVideoChoose

class InterVideoChooseAdapter : SingleTypeAdapter<InterVideoChoose>() {

    lateinit var onClick : OnItemClickListener
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {
        return InterVideoChooseViewHolder(ItemInterVideoChooseBinding.inflate(inflater,parent,false))
    }

    inner class InterVideoChooseViewHolder(view : ItemInterVideoChooseBinding) : SingleViewHolder<ItemInterVideoChooseBinding,InterVideoChoose>(view){
        override fun setHolder(entity: InterVideoChoose) {
            view.interVideoChooseTv.text = entity.content
            view.interVideoChooseTv.singleClick {
                val intent = Intent(context,InterVideoActivity::class.java)
                intent.putExtra("id",entity.id)
                intent.putExtra("type",entity.type)
//                if(entity.type != InterVideoActivity.FINISH) {
                    context.startActivity(intent)
//                }
                onClick.onItemClick()
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(){

        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        onClick = listener
    }
}