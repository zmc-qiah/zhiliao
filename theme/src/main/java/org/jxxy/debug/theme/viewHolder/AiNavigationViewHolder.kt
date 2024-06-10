package org.jxxy.debug.theme.viewHolder

import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jxxy.debug.common.service.WebService
import org.jxxy.debug.common.util.MoreViewAlpha1to0
import org.jxxy.debug.corekit.common.CommonServiceManager
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.dp
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.theme.bean.ThemeNavigation
import org.jxxy.debug.theme.bean.ThemeNavigationBean
import org.jxxy.debug.theme.databinding.ItemAiNavigationBinding
import org.jxxy.debug.theme.databinding.ItemNavigationLeftBinding
import org.jxxy.debug.theme.databinding.ItemNavigationRightBinding
import org.jxxy.debug.theme.floatball.utils.ShadowNode

class AiNavigationViewHolder(view:ItemAiNavigationBinding):MultipleViewHolder2<ItemAiNavigationBinding,ThemeNavigation>(view) {
    val adapter = object :SingleTypeAdapter<ThemeNavigationBean>(){
        override fun createViewHolder(
            viewType: Int,
            inflater: LayoutInflater,
            parent: ViewGroup
        ): RecyclerView.ViewHolder? = when (viewType) {
            0->{
                LeftVH(ItemNavigationLeftBinding
                    .inflate(inflater, parent,false))
            }
            else ->{
                RightVH(ItemNavigationRightBinding
                    .inflate(inflater, parent,false))
            }
        }
        override fun getItemViewType(position: Int): Int = position%2
    }
    var isCommonItemDecorationAdded = false

    override fun setHolder(entity: ThemeNavigation) {
        view.aiRv.adapter = adapter
        if (!isCommonItemDecorationAdded) {
            view.aiRv.addItemDecoration(CommonItemDecoration(40f))
            isCommonItemDecorationAdded = true
        }
        adapter.clearAndAdd(entity.list)
    }

    override fun setHolder(entity: ThemeNavigation, payload: Any) {
        if (payload as? Boolean?:false){
            GlobalScope.launch {
                someViewAlpha0to1(listOf(view.titleTv,view.descriptionTV),300)
                delay(600)
                    launch(Dispatchers.Main) {
                        for (i in 0..adapter.itemCount-1) {
                        adapter.notifyItemChanged(i,true)
                    }
                }
            }
        }else{
            for (i in adapter.itemCount-1 downTo  0) {
                MoreViewAlpha1to0(listOf(listOf(view.titleTv,view.descriptionTV)))
                adapter.notifyItemChanged(i,false)
            }
        }
    }
    class LeftVH(view:ItemNavigationLeftBinding):SingleViewHolder<ItemNavigationLeftBinding,ThemeNavigationBean>(view){
        override fun setHolder(entity: ThemeNavigationBean) {
            view.contentTv.text = entity.content
            view.titleTv.text = entity.name
            view.logoIV.load(entity.photo,6)
            view.root.singleClick {
                CommonServiceManager.service<WebService>()?.gotoWebH5(context,entity.url)
            }
            updateShadowView(view.bgView,
                ShadowNode(
                    235.dp(),
                    50.dp(),
                    10.dp(),
                    0,
                    0,
                    Color.BLACK
                )
            )
        }
        override fun setHolder(entity: ThemeNavigationBean, payload: Any) {
            if (payload as? Boolean?:false)view.root.transitionToEnd()
            else view.root.transitionToStart()
        }
    }
    class RightVH(view:ItemNavigationRightBinding):SingleViewHolder<ItemNavigationRightBinding,ThemeNavigationBean>(view){
        override fun setHolder(entity: ThemeNavigationBean) {
            view.contentTv.text = entity.content
            view.titleTv.text = entity.name
            view.logoIV.load(entity.photo,6)
            view.root.singleClick {
                CommonServiceManager.service<WebService>()?.gotoWebH5(context,entity.url)
            }
            updateShadowView(view.bgView,
                ShadowNode(
                    235.dp(),
                    50.dp(),
                    10.dp(),
                    0,
                    0,
                    Color.BLACK
                )
            )
        }
        override fun setHolder(entity: ThemeNavigationBean, payload: Any) {
            if (payload as? Boolean?:false)view.root.transitionToEnd()
            else view.root.transitionToStart()
        }
    }
}
fun updateShadowView(view: View, shadowNode: ShadowNode) {
    val shadowColor = shadowNode.shadowColor
    val shadowRadius = shadowNode.shadowRadius
    val shadowOffsetX = shadowNode.shadowOffsetX
    val shadowOffsetY = shadowNode.shadowOffsetY
    val shadowDrawable = GradientDrawable()
    shadowDrawable.setColor(Color.WHITE)
    shadowDrawable.cornerRadius = 45f
    shadowDrawable.setStroke(2, Color.LTGRAY)
    shadowDrawable.setSize(shadowNode.width, shadowNode.height)
    view.background = shadowDrawable
    ViewCompat.setElevation(view, shadowRadius.toFloat())
    val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.setMargins(shadowOffsetX, shadowOffsetY, shadowOffsetX, shadowOffsetY)
    view.layoutParams = layoutParams
    val shadowPaint = Paint()
    shadowPaint.color = shadowColor
    shadowPaint.style = Paint.Style.FILL
    shadowPaint.setShadowLayer(shadowRadius.toFloat(), shadowOffsetX.toFloat(), shadowOffsetY.toFloat(), shadowColor)
    view.setLayerType(View.LAYER_TYPE_SOFTWARE, shadowPaint)
}