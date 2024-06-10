package org.jxxy.debug.test.fragment.Service

import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.test.fragment.bean.AchievementRes
import org.jxxy.debug.test.fragment.bean.InfoRespone
import retrofit2.http.*

interface AchievementService {
    @GET("question/achievement/get")
    suspend fun getAchievement() : BaseResp<AchievementRes>

    @POST("question/achievement/show")
    suspend fun useAchievement(@Query("id") id : Int) : BaseResp<Int>

    @POST("question/achievement/do")
    suspend fun doneAchievement(@Body body : ArrayList<Int>) : BaseResp<AchievementRes>

    @GET("member/info/get")
    suspend fun getInfo(): BaseResp<InfoRespone>
}