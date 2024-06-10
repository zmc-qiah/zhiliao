package org.jxxy.debug.theme.bean

import org.jxxy.debug.corekit.http.CODE_LOGIN
import org.jxxy.debug.corekit.http.CODE_SUCCESS
import org.jxxy.debug.corekit.http.TokenManager
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.listener.CommonCallback
import org.jxxy.debug.corekit.http.listener.LiveDataCallback
import org.jxxy.debug.corekit.util.toast

class AiDrawResp<T>(
    val code: Int? = null,
    val info: String? = null,
    val data: T? = null,
) {
}

fun <T, D> AiDrawResp<D>.process(
    resLiveData: ResLiveData<T>,
    callback: LiveDataCallback<T, D>
): Boolean {
    when (code) {
        200 -> callback.success(resLiveData, this.info, this.data)
        CODE_LOGIN -> {
            info.toast()
            TokenManager.gotoLogin()
            return false
        }
        else ->
            callback.otherCode(resLiveData, this.code, this.info, this.data)
    }
    return true
}

fun <D> AiDrawResp<D>.process(
    callback: CommonCallback<D>
): Boolean {
    when (code) {
        200 -> callback.success(this.info, this.data)
        CODE_LOGIN -> {
            TokenManager.gotoLogin()
            return false
        }
        else ->
            callback.otherCode(this.code, this.info, this.data)
    }
    return true
}