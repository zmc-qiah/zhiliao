package org.jxxy.debug.member.http.service

import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.member.bean.PostComment
import org.jxxy.debug.member.http.respone.ForumCommentRespone
import org.jxxy.debug.member.http.respone.PreViewRes
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ResourceAPI {
    @GET("resource/comment2/get")
    suspend fun selectCommentComment(
        @Header("satoken") value: String,
        @Query("commentId")id:Int,@Query("start") start: Int, @Query("pageSize") pageSize: Int):BaseResp<ForumCommentRespone>
    @POST("resource/comment/post")
    suspend fun addComment(@Header("sotoken") value: String, @Query("isShared")isShared:Boolean = false,@Query("resourceId") id: Int, @Body comment: PostComment): BaseResp<ForumCommentRespone>
    @POST("resource/comment2/post")
    suspend fun addCommentInForum(@Header("sotoken") value: String, @Query("commentId") id: Int, @Body comment: PostComment): BaseResp<ForumCommentRespone>
    @GET("resource/mindMap/get")
    suspend fun getThinkMap(@Query("start") start: Int, @Query("pageSize") pageSize: Int = 15):BaseResp<PreViewRes>
}