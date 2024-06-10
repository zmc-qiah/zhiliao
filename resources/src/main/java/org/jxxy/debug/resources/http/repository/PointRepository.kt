package org.jxxy.debug.resources.http.repository

import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.resources.http.service.PointAPI

class PointRepository:BaseRepository<PointAPI>(PointAPI::class.java) {
    suspend fun addArticle():BaseResp<String?> = APiService.addArticle(value)
    suspend fun addView():BaseResp<String?> = APiService.addView(value)
}