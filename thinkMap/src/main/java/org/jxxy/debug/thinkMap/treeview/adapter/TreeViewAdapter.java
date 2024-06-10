package org.jxxy.debug.thinkMap.treeview.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import org.jxxy.debug.thinkMap.treeview.line.BaseLine;
import org.jxxy.debug.thinkMap.treeview.listener.TreeViewNotifier;
import org.jxxy.debug.thinkMap.treeview.model.NodeModel;
import org.jxxy.debug.thinkMap.treeview.model.TreeModel;

public abstract class TreeViewAdapter<T> {
    private TreeViewNotifier notifier;

    public TreeViewNotifier getNotifier() {
        return notifier;
    }

    private TreeModel<T> treeModel;

    public void setTreeModel(TreeModel<T> treeModel) {
        this.treeModel = treeModel;
        notifyDataSetChange();
    }

    /**
     * Get tree model
     * @return tree model
     */
    public TreeModel<T> getTreeModel(){
        return treeModel;
    }

    /**
     * For create view holder by your self
     * @param viewGroup parent
     * @param model node
     * @return holder
     */
    public abstract TreeViewHolder<T> onCreateViewHolder(@NonNull ViewGroup viewGroup, NodeModel<T> model);

    /**
     * when bind the holder, set up you view
     * @param holder holder
     */
    public abstract void onBindViewHolder(@NonNull TreeViewHolder<T> holder);

    /**
     * Draw line between node and node by you decision.
     * If you return an BaseLine, line will be draw by the return one instead of TreeViewLayoutManager's.
     * @param drawInfo provides all you need to draw you line
     * @return the line draw you want to use for different nodes
     */
    public abstract BaseLine onDrawLine(DrawInfo drawInfo);

    /**
     * for recycling holder, exactly for recycling views
     * @param node
     * @return
     */
    public int getHolderType(NodeModel<?> node){
        return 0;
    }

    public void setNotifier(TreeViewNotifier notifier){
        this.notifier = notifier;
    }

    public void notifyDataSetChange(){
        if(notifier!=null){
            notifier.onDataSetChange();
        }
    }

    public void notifyItemViewChange(NodeModel<T> node){
        if(notifier!=null){
            notifier.onItemViewChange(node);
        }
    }
}
