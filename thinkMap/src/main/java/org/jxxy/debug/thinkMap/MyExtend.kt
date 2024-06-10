package org.jxxy.debug.thinkMap // ktlint-disable filename

import org.jxxy.debug.thinkMap.treeview.adapter.TreeViewAdapter
import org.jxxy.debug.thinkMap.treeview.adapter.TreeViewHolder
import org.jxxy.debug.thinkMap.treeview.model.NodeModel

fun <T> TreeViewAdapter<T>.getTreeViewHolder(node: NodeModel<T>): TreeViewHolder<*>? = notifier.getTreeViewHolder(node)
