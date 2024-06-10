package org.jxxy.debug.home.Fragment.viewholder

import navigation
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Course
import org.jxxy.debug.home.Fragment.item.GridFour
import org.jxxy.debug.home.databinding.ItemNewsBinding

class CourseItemViewHolder (view: ItemNewsBinding) :
    SingleViewHolder<ItemNewsBinding, Course>(view) {
    override fun setHolder(entity: Course) {
        val List = entity.classInfos
        if (List != null && position < List.size) {
            val tool = List[position]
            view.news1.load(tool.photo)
            view.newsIntro1.text = tool.title
        }
    }
}