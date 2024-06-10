package org.jxxy.debug.thinkMap.MyListener
import org.jxxy.debug.thinkMap.bean.MofidyBean
import org.jxxy.debug.thinkMap.treeview.model.NodeModel

interface TreeNodeClickListener<T> {
    fun select(node: NodeModel<T>)
    fun clickDouble(node: NodeModel<T>)
    fun longClick(node: NodeModel<T>)
    fun deleteNode(bean: MofidyBean)
    fun addNode(entry: MofidyBean)
    fun titleChange(bean: MofidyBean)
}
