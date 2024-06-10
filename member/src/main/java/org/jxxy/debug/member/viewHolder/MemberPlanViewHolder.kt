package org.jxxy.debug.member.viewHolder

import android.graphics.drawable.Drawable
import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.member.R
import org.jxxy.debug.member.adapter.PlanAdapter
import org.jxxy.debug.member.bean.Plan
import org.jxxy.debug.member.databinding.ItemPlanListBinding

class MemberPlanViewHolder(binding: ItemPlanListBinding, private val onClick: (view: View) -> Unit) : MultipleViewHolder2<ItemPlanListBinding, Plan>(binding) {
    override fun setHolder(entity: Plan) {
        context
        val planAdapter = PlanAdapter()
        val s = "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/01/dfc35f66-3bbc-452f-96b9-2e3a38ef2f38.png"
        Glide.with(context)
            .load(s)
            .into(object : CustomTarget<Drawable>(){
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    view.bgTV.setImageDrawable(resource)
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        view.userPlanRV.adapter = planAdapter
        entity?.let {
            view.usePlanBtn.singleClick {
                onClick(it)
            }
            view.userPlanNameTV.text = it.name
            view.userPlanStartDateTimeTV.text = it.startTime?.let { it.split(" ")[0] }
            view.userPlanEndDateTimeTV.text = it.endTime?.let { it.split(" ")[0] }
            it.studyEvent?.let {
                it.forEach {
                    it.eventTime = it.eventTime?.let { it.split(" ")[0] }
                }
                if (it.size == 1) {
                    it[0].type = 3
                } else {
                    it[0].type = 0
                    it[it.size - 1].type = 2
                }
                planAdapter.clearAndAdd(it)
            }
            (context as? LifecycleCoroutineScope)?.let {

            }
            // 0未开始，1进行中，2已完成
            when (it.state?.toInt()) {
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
}
