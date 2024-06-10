package org.jxxy.debug.home.Fragment.viewholder

import androidx.lifecycle.LifecycleCoroutineScope
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.home.Fragment.adapter.HistoryAdapter
import org.jxxy.debug.home.Fragment.item.History
import org.jxxy.debug.home.Fragment.item.bean.HistoryBean
import org.jxxy.debug.home.databinding.ComponentHistoryBinding

class HistoryViewHolder(val binding: ComponentHistoryBinding,val scope: LifecycleCoroutineScope? =null) :
    MultipleViewHolder<History>(binding.root){
    val items: ArrayList<HistoryBean> = arrayListOf()
    private val historyAdapter= HistoryAdapter()
    private var itemCount=0
    private var list= listOf<HistoryBean>()
    init {

        binding.recyclerview.adapter = historyAdapter
        binding.recyclerview.addItemDecoration(CommonItemDecoration(8f))

    }

    override fun setHolder(entity: History) {
        binding.historyTitle.text = entity.typeName
        entity.aiHistory?.let { List ->
            list=List
            historyAdapter.clear()
            val tools = History(entity.type, entity.typeName,List)
            for (i in 1..80) {
                historyAdapter.add(tools)
            }
        }
        binding.HistoryViewSwitcher .initFactory(14f,false)
        scope?.let {
            binding.HistoryViewSwitcher.setData(list,
                it
            )
        }
    }
}