package org.jxxy.debug.test.fragment.bean

import androidx.annotation.NonNull
import java.io.Serializable

class HttpResult<T> : Serializable {
    private var resultCode = 0
    private var message: String? = null
    private var data: T? = null

    fun getResultCode(): Int {
        return resultCode
    }

    fun setResultCode(resultCode: Int) {
        this.resultCode = resultCode
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getData(): T? {
        return data
    }

    fun setData(data: T?) {
        this.data = data
    }

    @NonNull
    override fun toString(): String {
        return "HttpResult{" +
                "data=" + data +
                ", resultCode=" + resultCode +
                ", message=" + message + '\'' +
                '}'
    }
}