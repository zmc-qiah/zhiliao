package org.jxxy.debug.common.service

import android.content.Context
import org.jxxy.debug.corekit.common.CommonServiceManager

interface SearchEndService {
    fun goSearchEnd(context: Context,keyword :String)
    fun goSearchEnd(context: Context,flag:Int,keyword :String)
    fun gonSearchMiddle(context: Context)
}
fun goSearch(context: Context, keyword :String) {
    CommonServiceManager.service<SearchEndService>()?.goSearchEnd(context,keyword)
}

fun goSearch(context: Context, flag: Int,keyword :String) {
    CommonServiceManager.service<SearchEndService>()?.goSearchEnd(context,flag,keyword)
}
fun gonSearchMiddle(context: Context) {
    CommonServiceManager.service<SearchEndService>()?.gonSearchMiddle(context)
}