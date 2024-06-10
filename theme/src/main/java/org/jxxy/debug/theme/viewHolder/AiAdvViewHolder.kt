package org.jxxy.debug.theme.viewHolder

import androidx.recyclerview.widget.ItemTouchHelper
import com.dingmouren.layoutmanagergroup.echelon.EchelonLayoutManager
import com.dingmouren.layoutmanagergroup.slide.ItemTouchHelperCallback
import org.jxxy.debug.common.util.MoreViewAlpha1to0
import org.jxxy.debug.common.widget.SmileView
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.theme.adapter.AdvAdapter
import org.jxxy.debug.theme.bean.AiAdv
import org.jxxy.debug.theme.bean.AiAdvBean
import org.jxxy.debug.theme.databinding.ItemAiAdvBinding

class AiAdvViewHolder(view:ItemAiAdvBinding):MultipleViewHolder2<ItemAiAdvBinding,AiAdv>(view) {
    val adapter by lazy {
        AdvAdapter()
    }
    private lateinit var itemTouchHelperCallback:ItemTouchHelperCallback<AiAdvBean>
    private lateinit var touchHelper:ItemTouchHelper
    private lateinit var mSmileView:SmileView
    private var mLikeCount =20
    private var mDislikeCount =20
    private var flag = false
    override fun setHolder(entity: AiAdv) {
        view.aiRv.adapter=adapter
        adapter.clearAndAdd(entity.list)
        view.aiRv.layoutManager = EchelonLayoutManager(context)
    }

    override fun setHolder(entity: AiAdv, payload: Any) {
        if (payload as? Boolean ?: false ){
            view.run {
                someViewAlpha0to1(listOf(descriptionTV,aiRv))
            }
        }else{
            view.run {
//                someViewAlpha0to1(listOf(titleTv,descriptionTV,aiRv),flag = false)
                MoreViewAlpha1to0(listOf(listOf(descriptionTV,aiRv)))
            }
        }
    }
}