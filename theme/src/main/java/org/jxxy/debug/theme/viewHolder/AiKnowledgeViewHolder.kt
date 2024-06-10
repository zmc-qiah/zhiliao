package org.jxxy.debug.theme.viewHolder

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jxxy.debug.common.widget.FlexLayoutManager
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.theme.WaveView
import org.jxxy.debug.theme.adapter.AiKnowledgeAdapter
import org.jxxy.debug.theme.bean.AiKnowledge
import org.jxxy.debug.theme.databinding.ItemAiKnowledgeBinding

class AiKnowledgeViewHolder(view: ItemAiKnowledgeBinding) :
    MultipleViewHolder2<ItemAiKnowledgeBinding, AiKnowledge>(view) {
    var count = 0
    private val adapter by lazy {
        AiKnowledgeAdapter()
    }
    override fun setHolder(entity: AiKnowledge) {
        adapter.clearAndAdd(entity.list)
        view.itemAiKnowledgeRv.layoutManager = FlexLayoutManager()
        view.itemAiKnowledgeRv.adapter = adapter
        view.testWaveView.post {
            view.testWaveView.percent = 60
            view.testWaveView.lifeDelegate = WaveView.RESUME
        }
        view.root.transitionToEnd()
    }

    override fun setHolder(entity: AiKnowledge, payload: Any) {
        if(payload as? Boolean ?: false){
            view.testWaveView.post {
                view.testWaveView.percent = 60
                view.testWaveView.lifeDelegate = WaveView.RESUME
            }
            view.root.transitionToEnd()
            GlobalScope.launch {
                delay(600)
                val next = adapter.list.listIterator()
//                val last = adapter.list.listIterator(adapter.itemCount)
                while(next.hasNext()){
                    val next1 = next.next()
//                    val previous = last.previous()
                    val startIndex = next.previousIndex()
//                    val endIndex = last.nextIndex()
//                    if (startIndex>endIndex) break
                    launch(Dispatchers.Main) {
//                        if (startIndex!=endIndex){
//                            adapter.notifyItemChanged(startIndex,true)
//                            adapter.notifyItemChanged(endIndex,true)
//                        }else{
                            adapter.notifyItemChanged(startIndex,true)
//                        }
                    }
                   if (startIndex !=adapter.itemCount) delay(350)
                }
            }
        }else{
            view.root.transitionToStart()
            adapter.notifyItemRangeChanged(0,adapter.itemCount,false)
        }
    }
}