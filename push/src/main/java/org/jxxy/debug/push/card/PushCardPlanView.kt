package org.jxxy.debug.push.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import navigation
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.push.databinding.CardPushTypePlanBinding
import org.jxxy.debug.push.gson.PushCardDefaultEntity

class PushCardPlanView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleArr: Int = 0)
    : PushCardBaseView<PushCardDefaultEntity, CardPushTypePlanBinding>(context, attrs, defStyleArr)
{
    override fun bindData(entity: PushCardDefaultEntity) {
        entity.apply {
            view.planCardIv.load(iconUrl, 7)
            view.planCardTitleTv.text = title
            view.planCardContentTv.text = content
            if (buttonDesc.isNullOrEmpty()) {
                view.planCardBtn.gone()
            } else {
                view.planCardBtn.show()
                view.planCardBtn.text = buttonDesc
            }
            view.beginTimeTv.text = "From ${entity.beginTime}"
            view.endTimeTv.text = "To ${entity.endTime}"
        }
        super.bindData(entity)
    }

    override fun onClick() {
        if (entity?.buttonDesc.isNullOrEmpty()) {
            return
        }
        entity?.skipUrl?.navigation(context)
        super.onClick()
    }

    override fun bindView(inflater: LayoutInflater): CardPushTypePlanBinding {
        return CardPushTypePlanBinding.inflate(inflater, this, true)
    }
}