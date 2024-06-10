package org.jxxy.debug.theme.myListener

import android.util.Log
import org.jxxy.debug.theme.bean.StreamData

abstract class CommonStreamListener:StreamResponseListener {
    private var nowString  = StringBuffer()

    override fun onStreamDataReceived(data: StreamData) {
        Log.d("Stream的数据1", "onStreamDataReceived: ${data}")
        if(data.choices?.size?:0>0){
            data.choices[0].delta.content?.let {
                Log.d("Stream的数据2", "onStreamDataReceived: it${it}")
                if (it.length>0){
                    nowString.append(it)
                }
            }
        }
    }
    override fun isEnd() {
        val res = (nowString.toString())
        nowString = StringBuffer()
        onEnd(res)
    }
    abstract fun onEnd(res:String)
}