package org.jxxy.debug.home.Fragment.viewholder

import androidx.recyclerview.widget.GridLayoutManager
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.home.Fragment.adapter.BookAdapter
import org.jxxy.debug.home.Fragment.item.Book
import org.jxxy.debug.home.databinding.StudyComponentRecommendBookBinding


class BookViewHolder (val binding: StudyComponentRecommendBookBinding) :
    MultipleViewHolder<Book>(binding.root) {
    private val adapter = BookAdapter()

    init {
        binding.recyclerview.layoutManager = GridLayoutManager(itemView.context, 1)
        binding.recyclerview.addItemDecoration(SpanItemDecoration(10f, 10f, 1))
        binding.recyclerview.adapter = adapter
    }

    override fun setHolder(entity: Book) {
        binding.recommendBook.text = entity.typeName
        entity.aiBooks?.let { List ->
            adapter.clear()
            val tools = Book(17,"book", List)
            for (i in 1..80) {
                adapter.add(tools)
            }
        }
    }
}