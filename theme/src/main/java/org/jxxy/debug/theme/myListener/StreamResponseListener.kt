package org.jxxy.debug.theme.myListener

import org.jxxy.debug.theme.bean.StreamData

interface StreamResponseListener {
    fun onStreamDataReceived(data: StreamData)
    fun onStreamError(error: Throwable)
    fun isEnd()
}