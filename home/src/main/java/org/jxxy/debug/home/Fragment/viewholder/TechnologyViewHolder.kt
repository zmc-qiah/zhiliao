package org.jxxy.debug.home.Fragment.viewholder
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.home.Fragment.adapter.TechnologyAdapter
import org.jxxy.debug.home.Fragment.item.Technology
import org.jxxy.debug.home.Fragment.item.bean.TechnologyBean
import org.jxxy.debug.home.databinding.StudyComponentHotTechnologyBinding


class TechnologyViewHolder (val binding: StudyComponentHotTechnologyBinding,val scope: LifecycleCoroutineScope? =null) :
    MultipleViewHolder<Technology>(binding.root) {
    private val adapter = TechnologyAdapter()
    private var list= listOf<TechnologyBean>()

    init {
        binding.recyclerview.layoutManager = GridLayoutManager(itemView.context, 1)
        binding.recyclerview.addItemDecoration(SpanItemDecoration(10f, 0f, 1))
        binding.recyclerview.adapter = adapter
    }

    override fun setHolder(entity: Technology) {
        binding.technology.text = entity.typeName

        entity.technologyInfos?.let { List ->
            list=List
            adapter.clear()
            val tools = Technology(entity.type, entity.typeName,List)
            for (i in 1..80) {
                adapter.add(tools)
            }
        }

  /*      binding.HotTechnologyViewSwitcher.initFactory(14f,false)
        scope?.let {
            binding.HotTechnologyViewSwitcher.setData(list,
                it
            )
        }*/
    }
}