package org.jxxy.debug.thinkMap.treeview.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import org.jxxy.debug.thinkMap.treeview.layout.TreeLayoutManager;
import org.jxxy.debug.thinkMap.treeview.model.NodeModel;


public class TreeViewHolder<T> {
    private int holderLayoutType = TreeLayoutManager.LAYOUT_TYPE_NONE;
    private View view;
    private NodeModel<T> node;
    public TreeViewHolder(View view, @NonNull NodeModel<T> node) {
        this.view = view;
        this.node = node;
    }

    public NodeModel<T> getNode() {
        return node;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setNode(NodeModel<?> node) {
        this.node = (NodeModel<T>)node;
    }

    public int getHolderLayoutType() {
        return holderLayoutType;
    }

    public void setHolderLayoutType(int holderLayoutType) {
        this.holderLayoutType = holderLayoutType;
    }
}
