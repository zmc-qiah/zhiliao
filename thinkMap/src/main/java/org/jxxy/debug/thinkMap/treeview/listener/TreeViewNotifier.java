package org.jxxy.debug.thinkMap.treeview.listener;


import org.jxxy.debug.thinkMap.treeview.adapter.TreeViewHolder;
import org.jxxy.debug.thinkMap.treeview.model.NodeModel;

/**
 * @Author: 怪兽N
 * @Time: 2021/4/23
 * @Email: 674149099@qq.com
 * @WeChat: guaishouN
 * @Describe:
 */
public interface TreeViewNotifier{
    void onDataSetChange();
    void onRemoveNode(NodeModel<?> nodeModel);
    void onRemoveChildNodes(NodeModel<?> parentNode);
    void onItemViewChange(NodeModel<?> nodeModel);
    void onAddNodes(NodeModel<?> parent, NodeModel<?>... childNodes);
    TreeViewHolder<?> getTreeViewHolder(NodeModel<?> nodeModel);
}