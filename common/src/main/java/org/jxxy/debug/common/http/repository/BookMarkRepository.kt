package org.jxxy.debug.common.http.repository

import org.jxxy.debug.common.bean.AiBookMarkRes
import org.jxxy.debug.common.bean.BookMarkRes
import org.jxxy.debug.common.http.service.BookMarkService
import org.jxxy.debug.corekit.http.HttpManager
import org.jxxy.debug.corekit.http.bean.BaseResp

class BookMarkRepository {
    val service : BookMarkService by lazy {
        HttpManager.instance.service(BookMarkService::class.java)
    }

    suspend fun getBookMarkScheme(id : Int) : BaseResp<BookMarkRes>{
        return service.getBookMarkScheme(id)
    }

    suspend fun getAiBookMarkScheme(id : Int) : BaseResp<AiBookMarkRes>{
        return service.getAiBookMarkScheme(id)
    }

}