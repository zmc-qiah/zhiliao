package org.jxxy.debug.home.Fragment.viewholder

import navigation
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Book
import org.jxxy.debug.home.Fragment.item.Course
import org.jxxy.debug.home.databinding.ItemBooksBinding
import org.jxxy.debug.home.databinding.ItemNewsBinding

class BookItemViewHolder (view: ItemBooksBinding) :
    SingleViewHolder<ItemBooksBinding, Book>(view) {
    override fun setHolder(entity: Book) {
        val List = entity.aiBooks
        if (List != null && position < List.size) {
            val tool = List[position]
            view.im.load(tool.photo)
            view.tv1.text = tool.title
            view.tv2.text=tool.describe
            view.root.singleClick {
                tool?.scheme?.navigation(context)
            }
        }
    }
}