package org.jxxy.debug.test.fragment.bean

class PkQuestion(){
    val questions:ArrayList<Question> ?= null
    val count:Int ?=null
    val userInfos:List<UserInfo>?=null
    override fun toString(): String {
        return "PkQuestion(questions=$questions, count=$count, userInfos=$userInfos)"
    }

}