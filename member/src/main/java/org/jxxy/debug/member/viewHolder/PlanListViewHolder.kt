package org.jxxy.debug.member.viewHolder

import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.member.R
import org.jxxy.debug.member.adapter.PlanAdapter
import org.jxxy.debug.member.bean.Plan
import org.jxxy.debug.member.databinding.ItemPlanListBinding

class PlanListViewHolder(val binding: ItemPlanListBinding) : SingleViewHolder<ItemPlanListBinding, Plan>(binding) {
    override fun setHolder(entity: Plan) {
        view.usePlanBtn.gone()
        val s =
            "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/01/dfc35f66-3bbc-452f-96b9-2e3a38ef2f38.png"
        Glide.with(context)
            .load(s)
            .into(object :CustomTarget<Drawable>(){
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    view.bgTV.setImageDrawable(resource)
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        val planAdapter = PlanAdapter()
        view.userPlanRV.adapter = planAdapter
        view.userPlanNameTV.text = entity.name
        view.userPlanStartDateTimeTV.text = entity.startTime!!.split(" ")[0]
        view.userPlanEndDateTimeTV.text = entity.endTime!!.split(" ")[0]
        entity.studyEvent?.let {
            it.forEach {
                it.eventTime = it.eventTime!!.split(" ")[0]
            }
            if (it.size == 1) {
                it[0].type = 3
            } else {
                it[0].type = 0
                it[it.size - 1].type = 2
            }
            planAdapter.clearAndAdd(it)
        }
        // 0未开始，1进行中，2已完成
        when (entity.state?.toInt()) {
            0 -> {
                view.userPlanStateIcon.setTextColor(ResourceUtil.getColor(R.color.black))
                view.userPlanStateIcon.text = ResourceUtil.getString(R.string.incomplete)
                view.userPlanStateIcon.scaleX =0.8f
                view.userPlanStateIcon.scaleY =0.8f
            }
            1 -> {
                view.userPlanStateIcon.setTextColor(ResourceUtil.getColor(R.color.task_button))
                view.userPlanStateIcon.text = ResourceUtil.getString(R.string.progress)
                view.userPlanStateIcon.scaleX =1f
                view.userPlanStateIcon.scaleY =1f
            }
            2 -> {
                view.userPlanStateIcon.setTextColor(ResourceUtil.getColor(R.color.green))
                view.userPlanStateIcon.text = ResourceUtil.getString(R.string.getData)
                view.userPlanStateIcon.scaleX =1f
                view.userPlanStateIcon.scaleY =1f
            }
        }
    }
}
