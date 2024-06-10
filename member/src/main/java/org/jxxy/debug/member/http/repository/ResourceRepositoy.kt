package org.jxxy.debug.member.http.repository

import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.member.bean.PostComment
import org.jxxy.debug.member.bean.Preview
import org.jxxy.debug.member.http.respone.ForumCommentRespone
import org.jxxy.debug.member.http.service.ResourceAPI

class ResourceRepositoy:BaseRepository<ResourceAPI>(ResourceAPI::class.java) {
    suspend fun selectCommentComment
                (id:Int,
                 start: Int,
                 pageSize: Int): BaseResp<ForumCommentRespone> = APiService.selectCommentComment(value, id, start, pageSize)

    suspend fun addCommentInForum(id:Int, comment:PostComment):BaseResp<ForumCommentRespone> = APiService.addCommentInForum(value, id, comment)
    suspend fun getThinkMap(start: Int):BaseResp<List<Preview>> {
        val thinkMap = APiService.getThinkMap(start)
        return BaseResp(thinkMap.code,thinkMap.message,thinkMap.data?.list)
//        return BaseResp(thinkMap.code,thinkMap.message, listOf(
//            Preview(1,"哈哈哈哈哈",0,"啊啊啊啊啊啊","https://i0.hdslb.com/bfs/archive/c095bfbe7421d66f52c62b5d06bc9bea15f4dd6f.jpg@672w_378h_1c_!web-home-common-cover.webp"),
//            Preview(1,"哈哈哈哈哈",1,"啊啊啊啊啊啊","https://i0.hdslb.com/bfs/archive/c095bfbe7421d66f52c62b5d06bc9bea15f4dd6f.jpg@672w_378h_1c_!web-home-common-cover.webp"),
//            ))
    }

}