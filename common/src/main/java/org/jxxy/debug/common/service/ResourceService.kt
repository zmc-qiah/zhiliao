package org.jxxy.debug.common.service

import android.content.Context
import org.jxxy.debug.corekit.common.CommonServiceManager

interface ResourceService {
    fun gotoResourcePage(context: Context, id: Int)
}
fun goResource(context: Context, id: Int) {
    CommonServiceManager.service<ResourceService>()?.gotoResourcePage(context, id)
}


