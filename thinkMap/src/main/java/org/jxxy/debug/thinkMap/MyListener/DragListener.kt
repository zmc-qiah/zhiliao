package org.jxxy.debug.thinkMap.MyListener

import org.jxxy.debug.common.bean.Node
import org.jxxy.debug.thinkMap.treeview.model.NodeModel

interface DragListener {
    fun removeNode(parent:NodeModel<*>?,childNode: NodeModel<*>?,newP:NodeModel<*>? )
}