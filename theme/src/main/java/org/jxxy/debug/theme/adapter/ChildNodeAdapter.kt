package org.jxxy.debug.theme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.theme.databinding.ItemChildNodeBinding

class ChildNodeAdapter:SingleTypeAdapter<String>() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? = object :SingleViewHolder<ItemChildNodeBinding,String>(
        ItemChildNodeBinding.inflate(inflater,parent,false)){
        override fun setHolder(entity: String) {
            view.TextView.text = entity
        }
    }
    val list:List<String>
        get(){
            return data
        }
}