package org.jxxy.debug.theme.http.service

import org.jxxy.debug.theme.bean.ChatBody
import org.jxxy.debug.theme.http.resopne.ChatRespone
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AI2DService {
    @POST("v1/chat/completions")
    @Headers("Authorization:fk213310-8AdUDoJBZoIKFXnzfG6cdWQYiq22xjAA","Content-Type:application/json")
    suspend fun multipleChat(@Body chatBody: ChatBody): ChatRespone
}