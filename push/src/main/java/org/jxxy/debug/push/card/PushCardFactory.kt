package org.jxxy.debug.push.card

import android.content.Context
import org.jxxy.debug.push.gson.PushCardBaseBean
import org.jxxy.debug.push.gson.PushCardDefaultEntity

class PushCardFactory {
    fun makeCard(entity: PushCardBaseBean, context: Context, callback: CardViewCallback): PushCardBaseView<PushCardBaseBean, *>? {
        val view: PushCardBaseView<PushCardBaseBean, *>? = when ((entity as PushCardDefaultEntity).show) {
            1 -> PushCardDefaultView(context)
            2 -> PushCardPlanView(context)
            else -> null
        }?.castToTarget()

        view?.let {
            it.bindData(entity)
            it.listener = callback
        }
        return view
    }
}

inline fun <reified T> Any.castToTarget(): T? {
    return this as? T
}
