package org.jxxy.debug.test.fragment.repository

import org.jxxy.debug.corekit.http.HttpManager
import org.jxxy.debug.corekit.http.TokenManager
import org.jxxy.debug.corekit.http.bean.BaseResp
import org.jxxy.debug.test.fragment.Service.TestService
import org.jxxy.debug.test.fragment.bean.*

class QuestionRepository {
    val value: String
        get() {
            return TokenManager.getToken().toString()
        }

    private val service: TestService by lazy {
        HttpManager.instance.service(TestService::class.java)
    }

    suspend fun getQuestion(count: Int): BaseResp<Questions> {
        return service.getQuestion(value, count)
    }

    suspend fun addCollection(questionId: Int): BaseResp<Int> {
        return service.addCollection(value, questionId)
    }

    suspend fun deleteCollection(questionId: Int): BaseResp<Int> {
        return service.deleteCollection(value, questionId)
    }

    suspend fun addMistake(questionId: Int): BaseResp<Int> {
        return service.addMistake(value, questionId)
    }

    suspend fun getMistake(): BaseResp<CollectionOrMistakeQuestions> {
        return service.getMistake(value)
    }

    suspend fun getCollection(): BaseResp<CollectionOrMistakeQuestions> {
        return service.getCollection(value)
    }

    suspend fun getSpecialQuestion(
        category: String,
        start: Int,
        pageSize: Int
    ): BaseResp<SpecialQuestions> {
        return service.getSpecialQuestion(value, category, start, pageSize)
    }

    suspend fun getQuestionById(questionId: Int): BaseResp<Question> {
        return service.getQuestionById(value, questionId)
    }

    suspend fun addAnswer(): BaseResp<Int> {
        return service.addAnswer(value)
    }

    suspend fun deleteMistake(questionId: Int): BaseResp<Int> {
        return service.deleteMistake(value, questionId)
    }

    suspend fun getStatistics(): BaseResp<StatisticsContent> {
        return service.getStatistics(value)
    }

    suspend fun getSpecialOut(): BaseResp<SpecialInfo> {
        return service.getSpecialOut(value)
    }

    suspend fun getSpecialInner(category: String,start: Int,pageSize: Int): BaseResp<SpecialInner> {
        return service.getSpecialInner(value,category,start,pageSize)
    }

    suspend fun postAnswer(type: Int,questionId: Int): BaseResp<Int>{
        return service.postAnswer(value,type,questionId)
    }

    suspend fun getSpecialRank(specialId : Int) : BaseResp<SpecialRanks>{
        return service.getSpecialRank(value,specialId)
    }

    suspend fun postSpecialScore(specialId : Int, time : Int, score : Int) : BaseResp<Int>{
        return service.postSpecialScore(value,specialId,time,score)
    }
}
