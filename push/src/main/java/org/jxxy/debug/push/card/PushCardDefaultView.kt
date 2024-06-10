package org.jxxy.debug.push.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import navigation
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.push.databinding.CardPushTypeDefaultBinding
import org.jxxy.debug.push.gson.PushCardDefaultEntity

class PushCardDefaultView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleArr: Int = 0) : PushCardBaseView<PushCardDefaultEntity, CardPushTypeDefaultBinding>(context, attrs, defStyleArr) {
    companion object {
        private const val VALUE_DEFAULT = "无文案"
    }

    override fun bindData(entity: PushCardDefaultEntity) {
        entity.apply {
            view.cardIconImg.load(iconUrl, 7)
            view.cardTitleTv.text = title
            view.cardContentTv.text = content
            if (buttonDesc.isNullOrEmpty()) {
                view.cardActionBtn.gone()
            } else {
                view.cardActionBtn.show()
                view.cardActionBtn.text = buttonDesc
            }
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

    override fun bindView(inflater: LayoutInflater): CardPushTypeDefaultBinding {
        return CardPushTypeDefaultBinding.inflate(inflater, this, true)
    }
}
