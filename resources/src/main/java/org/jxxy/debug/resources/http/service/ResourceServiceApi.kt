package org.jxxy.debug.resources.http.service

import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.resources.bean.ChapterCommentBody
import org.jxxy.debug.resources.bean.ChapterCommentResponse
import org.jxxy.debug.resources.bean.PostComment
import org.jxxy.debug.resources.bean.TagInfo
import org.jxxy.debug.resources.http.response.CommentResponse
import org.jxxy.debug.resources.http.response.RecommendInfoResponse
import org.jxxy.debug.resources.http.response.ResourceInfoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ResourceServiceApi {
    @GET("resource/getDetail")
    suspend fun getDetailById(@Header("satoken") value: String, @Query("resourceId") id: Int): BaseResp<ResourceInfoResponse>
    @GET("resource/recommend/get")
    suspend fun getRecommend(@Header("satoken") value: String): BaseResp<RecommendInfoResponse>
    @GET("resource/comment/get")
    suspend fun getComment(@Header("satoken") value: String, @Query("resourceId") id: Int, @Query("start") start: Int, @Query("pageSize") pageSize: Int): BaseResp<CommentResponse>
    @POST("resource/comment/post")
    suspend fun addComment(@Query("resourceId") id: Int, @Body comment: PostComment,@Query("isShared")isShared:Int = 0): BaseResp<CommentResponse>
    @POST("resource/like/add")
    suspend fun addLike(@Header("satoken") value: String, @Query("co") id: Int): BaseResp<Int>
    @POST("resource/like/cancel")
    suspend fun cancelLike(@Header("satoken") value: String, @Query("resourceId") id: Int): BaseResp<Int>

    @POST("resource/collection/add")
    suspend fun addCollection(@Header("satoken") value: String, @Query("resourceId") id: Int): BaseResp<Int>

    @POST("resource/collection/cancel")
    suspend fun cancelCollection(@Header("satoken") value: String, @Query("resourceId") id: Int): BaseResp<Int>
    @POST("member/note/post")
    suspend fun addNote(@Header("satoken") value: String, @Query("resourceId") id: Int,@Query("content")content: String): BaseResp<Int>
    @GET("resource/tag/comment/get")
    suspend fun getChapterComment(@Query("id")id:Int):BaseResp<ChapterCommentResponse>
    @POST("resource/tag/post")
    suspend fun addChapter(@Body tagInfo: TagInfo):BaseResp<List<TagInfo>>
    @POST("resource/tag/comment/post")
    suspend fun addChapterComment(@Body comment: ChapterCommentBody):BaseResp<ChapterCommentResponse>
}
