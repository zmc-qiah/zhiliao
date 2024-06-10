package org.jxxy.debug.test.fragment.bean

class QuestionVideoInner(
    val videoTitle: String,
    val videoUrl: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val answer : String,
    val videoResultList : ArrayList<QuestionVideoResult>
) {
}