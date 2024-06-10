package org.jxxy.debug.push.getui

import org.jxxy.debug.push.gson.GeTuiBaseBean
import org.jxxy.debug.push.gson.PushCardBaseBean

class GeTuiPushFactory private constructor() {
    private val cacheFactory: HashMap<String, GeTuiPushBaseImpl> = hashMapOf()

    companion object {
        val instance: GeTuiPushFactory by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            GeTuiPushFactory()
        }
    }

    fun handlePush(entity: GeTuiBaseBean) {
        val name = entity::class.simpleName.orEmpty()
        val factoryImpl: GeTuiPushBaseImpl?
        if (cacheFactory.containsKey(name)) {
            factoryImpl = cacheFactory[name]
        } else {
            val impl: GeTuiPushBaseImpl = when (entity) {
                is PushCardBaseBean -> {
                    GeTuiPushCardImpl()
                }
                else -> {
                    // do nothing
                    GeTuiPushBaseImpl()
                }
            }
            cacheFactory[name] = impl
            factoryImpl = impl
        }
        factoryImpl?.handlePush(entity)
    }
}
