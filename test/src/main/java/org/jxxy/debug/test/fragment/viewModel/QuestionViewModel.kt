package org.jxxy.debug.test.fragment.viewModel

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData
import org.jxxy.debug.common.http.BaseLiveDataCallback2
import org.jxxy.debug.corekit.http.BaseViewModel
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.request
import org.jxxy.debug.test.fragment.adapter.QuestionRvAdapter
import org.jxxy.debug.test.fragment.bean.*
import org.jxxy.debug.test.fragment.repository.QuestionRepository

class QuestionViewModel(application: Application) : BaseViewModel(application) {

    val repository: QuestionRepository by lazy {
        QuestionRepository()
    }
    val questionLiveData: ResLiveData<Questions> by lazy {
        ResLiveData()
    }
    val addCollectionLiveData: ResLiveData<Int> by lazy {
        ResLiveData()
    }
    val deleteCollectionLiveData: ResLiveData<Int> by lazy {
        ResLiveData()
    }
    val addMistakeLiveData: ResLiveData<Int> by lazy {
        ResLiveData()
    }
    val getMistakeLiveData: ResLiveData<CollectionOrMistakeQuestions> by lazy {
        ResLiveData()
    }
    val getCollectionLiveData: ResLiveData<CollectionOrMistakeQuestions> by lazy {
        ResLiveData()
    }
    val getSpecialQuestionLiveData: ResLiveData<SpecialQuestions> by lazy {
        ResLiveData()
    }
    val getQuestionByIdLiveData: ResLiveData<Question> by lazy {
        ResLiveData()
    }
    val addAnswerLiveData: ResLiveData<Int> by lazy {
        ResLiveData()
    }
    val deleteMistakeLiveData: ResLiveData<Int> by lazy {
        ResLiveData()
    }
    val getStatisticsLiveData: ResLiveData<StatisticsContent> by lazy {
        ResLiveData()
    }
    val getSpecialOutLiveData: ResLiveData<SpecialInfo> by lazy {
        ResLiveData()
    }
    val getSpecialInnerLiveData: ResLiveData<SpecialInner> by lazy {
        ResLiveData()
    }
    val postAnswerLiveData: ResLiveData<Int> by lazy {
        ResLiveData()
    }
    val getSpecialRankLiveData : ResLiveData<SpecialRanks> by lazy {
        ResLiveData()
    }
    val postSpecialScoreLiveData : ResLiveData<Int> by lazy {
        ResLiveData()
    }
    val chooseMap: HashMap<Int, String> = HashMap()
    val itemState: ArrayList<Int> = ArrayList()
    val isGetQuestion: ArrayList<Boolean> = ArrayList()
    var rightCount = MutableLiveData<Int>(0)
    var wrongCount = MutableLiveData<Int>(0)
    var time = MutableLiveData<Int>(0)
    var type = MutableLiveData<Int>(QuestionRvAdapter.TAKE)
    var index = MutableLiveData(0)
    var sum = 0
    var specialType = ""
    var collectionState = MutableLiveData(0)
    var answerType = 0
    var startTime = 0L
    var endTime = 0L
    var way: () -> Unit = {

    }
    lateinit var errorMedia : MediaPlayer
    lateinit var trueMedia : MediaPlayer
    var changeBossWay : (Int) -> Unit = {

    }

    fun getQuestions(count: Int) {
        request(
            questionLiveData,
            object : BaseLiveDataCallback2<Questions> {}
        ) {
            repository.getQuestion(count)
        }
    }

    fun addCollection(questionId: Int) {
        request(
            addCollectionLiveData,
            object : BaseLiveDataCallback2<Int> {}
        ) {
            repository.addCollection(questionId)
        }
    }

    fun deleteCollection(questionId: Int) {
        request(
            deleteCollectionLiveData,
            object : BaseLiveDataCallback2<Int> {}
        ) {
            repository.deleteCollection(questionId)
        }
    }

    fun addMistake(questionId: Int) {
        request(
            addMistakeLiveData,
            object : BaseLiveDataCallback2<Int> {}
        ) {
            repository.addMistake(questionId)
        }
    }

    fun getMistake() {
        request(
            getMistakeLiveData,
            object : BaseLiveDataCallback2<CollectionOrMistakeQuestions> {}
        ) {
            repository.getMistake()
        }
    }

    fun getCollection() {
        request(
            getCollectionLiveData,
            object : BaseLiveDataCallback2<CollectionOrMistakeQuestions> {}
        ) {
            repository.getCollection()
        }
    }

    fun getSpecialQuestion(category: String, start: Int, pageSize: Int) {
        request(
            getSpecialQuestionLiveData,
            object : BaseLiveDataCallback2<SpecialQuestions> {}
        ) {
            repository.getSpecialQuestion(category, start, pageSize)
        }
    }

    fun getQuestionById(questionId: Int) {
        request(
            getQuestionByIdLiveData,
            object : BaseLiveDataCallback2<Question> {}
        ) {
            repository.getQuestionById(questionId)
        }
    }

    fun addAnswer() {
        request(
            addAnswerLiveData,
            object : BaseLiveDataCallback2<Int> {}
        ) {
            repository.addAnswer()
        }
    }

    fun deleteMistake(questionId: Int) {
        request(
            deleteMistakeLiveData,
            object : BaseLiveDataCallback2<Int> {}
        ) {
            repository.deleteMistake(questionId)
        }
    }

    fun getStatistics() {
        request(
            getStatisticsLiveData,
            object : BaseLiveDataCallback2<StatisticsContent> {}
        ) {
            repository.getStatistics()
        }
    }

    fun getSpecialOut() {
        request(
            getSpecialOutLiveData,
            object : BaseLiveDataCallback2<SpecialInfo> {}
        ) {
            repository.getSpecialOut()
        }
    }

    fun getSpecialInner(category: String, start: Int, pageSize: Int) {
        request(
            getSpecialInnerLiveData,
            object : BaseLiveDataCallback2<SpecialInner> {}
        ) {
            repository.getSpecialInner(category, start, pageSize)
        }
    }

    fun postAnswer(type: Int, questionId: Int) {
        request(
            postAnswerLiveData,
            object : BaseLiveDataCallback2<Int> {}
        ) {
            repository.postAnswer(type, questionId)
        }
    }

    fun getSpecialRank(specialId : Int){
        request(
            getSpecialRankLiveData,
            object : BaseLiveDataCallback2<SpecialRanks>{}
        ){
            repository.getSpecialRank(specialId)
        }
    }

    fun postSpecialScore(specialId : Int,time : Int,score : Int){
        request(
            postSpecialScoreLiveData,
            object : BaseLiveDataCallback2<Int>{}
        ){
            repository.postSpecialScore(specialId,time,score)
        }
    }
}