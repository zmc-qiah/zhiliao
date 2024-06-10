package org.jxxy.debug.common.http

import org.jxxy.debug.corekit.http.bean.ErrorResponse
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.listener.LiveDataCallback

interface BaseLiveDataCallback2<Respone> : LiveDataCallback<Respone, Respone> {
    override fun error(emit: ResLiveData<Respone>, e: ErrorResponse) {
        emit.error(e, null)
    }

    override fun otherCode(
        emit: ResLiveData<Respone>,
        code: Int?,
        msg: String?,
        data: Respone?
    ) {
        emit.error(ErrorResponse.otherCode(code, msg))
    }

    override fun success(emit: ResLiveData<Respone>, msg: String?, data: Respone?) {
        data?.let { emit.success(it) }
    }

}
