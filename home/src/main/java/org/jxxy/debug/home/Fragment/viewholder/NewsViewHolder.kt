package org.jxxy.debug.home.Fragment.viewholder

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.adapter.NewsAdapter
import org.jxxy.debug.home.Fragment.item.News
import org.jxxy.debug.home.Fragment.item.bean.NewsBean
import org.jxxy.debug.home.databinding.ComponentNewsBinding
import org.jxxy.debug.home.databinding.ItemNewsBinding

class NewsViewHolder (val binding: ComponentNewsBinding) :
    MultipleViewHolder<News>(binding.root) {
    private val adapter = object :SingleTypeAdapter<NewsBean>(){
        override fun createViewHolder(
            viewType: Int,
            inflater: LayoutInflater,
            parent: ViewGroup
        ): RecyclerView.ViewHolder? =
            object :SingleViewHolder<ItemNewsBinding,NewsBean>(ItemNewsBinding.inflate(
            inflater,
            parent,
            false
        )){
            override fun setHolder(entity: NewsBean) {
                Log.d("newInfos", "setHolder: $entity")
                view.news1.load(entity.photo,16)
                view.newsIntro1.text = entity.title
                view.root.singleClick {
                    entity?.scheme?.navigation(context)
                }
            }
        }
    }
    override fun setHolder(entity: News) {
        binding.recyclerview.addItemDecoration(SpanItemDecoration(10f, 15f, 2))
        binding.recyclerview.adapter = adapter
        binding.newsTitle.text = entity.typeName
        entity.newInfos?.let { List ->
            Log.d("newInfos", "setHolder: ${List}")
            adapter.clearAndAdd(List)
        }
    }

}