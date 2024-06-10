package org.jxxy.debug.thinkMap.treeview

import androidx.viewbinding.ViewBinding
import org.jxxy.debug.thinkMap.bean.MofidyBean
import org.jxxy.debug.thinkMap.treeview.adapter.TreeViewHolder
import org.jxxy.debug.thinkMap.treeview.model.NodeModel

abstract class BaseTreeViewHolder<T, VB : ViewBinding>(val binding: VB, node: NodeModel<T>) : TreeViewHolder<T>(binding.root, node){
    abstract fun setHolder(entry:T)
    open fun update(entry: MofidyBean){
    }
}
