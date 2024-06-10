package org.jxxy.debug.test.fragment.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class Question(
    val id: Int,
    val questionType: Int,
    val questionShow: Int,
    val questionText: String,
    val questionPhoto: String? = null,
    val optionA : String,
    val optionB : String,
    val optionC : String?,
    val optionD : String?,
    val parse : String?,
    val answer : String,
    var collectState : Int?
) :
    MultipleType {
    override fun viewType() = questionType
}