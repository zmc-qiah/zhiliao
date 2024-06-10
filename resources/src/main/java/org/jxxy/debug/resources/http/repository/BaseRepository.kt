package org.jxxy.debug.resources.http.repository

import org.jxxy.debug.corekit.http.HttpManager
import org.jxxy.debug.corekit.http.TokenManager

open class BaseRepository<T>(val serviceClass: Class<T>) {
    val APiService: T by lazy {
        HttpManager.instance.service(serviceClass)
    }
    val value: String
        get() {
            return TokenManager.getToken().toString()
        }

}
