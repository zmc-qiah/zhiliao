package org.jxxy.debug.theme.viewHolder

import android.animation.ObjectAnimator
import android.view.View
import com.dingmouren.layoutmanagergroup.echelon.EchelonLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jxxy.debug.common.util.MoreViewAlpha1to0
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.theme.adapter.EmoAdapter
import org.jxxy.debug.theme.bean.AiEmo
import org.jxxy.debug.theme.databinding.ItemThemeEmoBinding

class AiEmoViewHolder(view:ItemThemeEmoBinding):MultipleViewHolder2<ItemThemeEmoBinding,AiEmo>(view) {
    val adapter by lazy { EmoAdapter() }
    override fun setHolder(entity: AiEmo) {
        view.aiRv.adapter = adapter
        adapter.clearAndAdd(entity.list)
        view.aiRv.layoutManager = EchelonLayoutManager(context)
    }

    override fun setHolder(entity: AiEmo, payload: Any) {
        val list = listOf(view.descriptionTV,view.aiRv)
        if (payload as? Boolean ?: false){
            someViewAlpha0to1(list)
        }else{
            MoreViewAlpha1to0(listOf(list),200)
        }
    }
}
fun someViewAlpha0to1(list: List<View>,time:Long = 400,lastTime:Long = 700,flag: Boolean = true){
    var s = 0f
    var e = 1f
    if (!flag){
        e = 0f
        s = 1f
    }
    GlobalScope.launch {
        for (i in 0..list.size-1) {
            var temp = time
            if ( i == list.size - 1 ) temp = lastTime
            launch(Dispatchers.Main) {
                ObjectAnimator.ofFloat(
                    list[i],"alpha",s,e
                ).apply {
                    duration = temp
                    start()
                }
            }
            if(temp == time) delay(temp)
        }
    }
}