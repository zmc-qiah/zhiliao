package org.jxxy.debug.resources.http.repository

import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.resources.bean.ChapterCommentBody
import org.jxxy.debug.resources.bean.ChapterCommentResponse
import org.jxxy.debug.resources.bean.PostComment
import org.jxxy.debug.resources.bean.TagInfo
import org.jxxy.debug.resources.http.response.CommentResponse
import org.jxxy.debug.resources.http.response.RecommendInfoResponse
import org.jxxy.debug.resources.http.response.ResourceInfoResponse
import org.jxxy.debug.resources.http.service.ResourceServiceApi

class ResourceRepository : BaseRepository<ResourceServiceApi>(ResourceServiceApi::class.java) {
    suspend fun getResourceById(id: Int): BaseResp<ResourceInfoResponse> = APiService.getDetailById(value, id)
    suspend fun getRecommend(): BaseResp<RecommendInfoResponse> = APiService.getRecommend(value)
    suspend fun addLike(id: Int): BaseResp<Int> = APiService.addLike(value, id)
    suspend fun cancelLike(id: Int): BaseResp<Int> = APiService.cancelLike(value, id)
    suspend fun cancelCollection(id: Int): BaseResp<Int> = APiService.addCollection(value, id)
    suspend fun addCollection(id: Int): BaseResp<Int> = APiService.cancelCollection(value, id)
    suspend fun getComment(id: Int, start: Int, page: Int): BaseResp<CommentResponse> = APiService.getComment(value, id, start, page)
    suspend fun addComment(id: Int,comment: String, commentPhoto: String = "",isShared:Int = 0): BaseResp<CommentResponse> = APiService.addComment(id,PostComment(comment),isShared)
    suspend fun getChapterComment(id: Int): BaseResp<ChapterCommentResponse> = APiService.getChapterComment(id)
    suspend fun addChapter(tagInfo: TagInfo): BaseResp<List<TagInfo>> = APiService.addChapter(tagInfo)
    suspend fun addChapterComment(body: ChapterCommentBody): BaseResp<ChapterCommentResponse> = APiService.addChapterComment(body)
}
