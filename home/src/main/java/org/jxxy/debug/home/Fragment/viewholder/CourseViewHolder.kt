package org.jxxy.debug.home.Fragment.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.home.Fragment.adapter.CourseAdapter
import org.jxxy.debug.home.Fragment.item.Course
import org.jxxy.debug.home.Fragment.item.bean.CourseBean
import org.jxxy.debug.home.databinding.ComponentNewsBinding
import org.jxxy.debug.home.databinding.ItemNewsBinding

class CourseViewHolder (val binding: ComponentNewsBinding) :
    MultipleViewHolder<Course>(binding.root) {
    private val adapter = object :SingleTypeAdapter<CourseBean>(){
        override fun createViewHolder(
            viewType: Int,
            inflater: LayoutInflater,
            parent: ViewGroup
        ): RecyclerView.ViewHolder?  = object :SingleViewHolder<ItemNewsBinding,CourseBean>(
            ItemNewsBinding.inflate(
                inflater,
                parent,
                false
            )
        ){
            override fun setHolder(entity: CourseBean) {
                view.news1.load(entity.photo)
                view.newsIntro1.text = entity.title
            }

        }

    }
    override fun setHolder(entity: Course) {
        binding.recyclerview.layoutManager = GridLayoutManager(itemView.context, 2)
        binding.recyclerview.addItemDecoration(SpanItemDecoration(10f, 15f, 2))
        binding.recyclerview.adapter = adapter
        binding.newsTitle.text = entity.typeName
        binding.newsTitle.setText("推荐课程")
        entity.classInfos?.let { List ->
            adapter.clearAndAdd(List)
        }
    }
}