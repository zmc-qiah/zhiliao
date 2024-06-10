package org.jxxy.debug.member.http

import org.jxxy.debug.corekit.http.bean.ErrorResponse
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.listener.LiveDataCallback

interface BaseLiveDataCallback<LiveData, Response> : LiveDataCallback<LiveData, Response> {
    override fun error(emit: ResLiveData<LiveData>, e: ErrorResponse) {
        emit.error(e, null)
    }

    override fun otherCode(
        emit: ResLiveData<LiveData>,
        code: Int?,
        msg: String?,
        data: Response?
    ) {
        emit.error(ErrorResponse.otherCode(code, msg))
    }
}
