package org.jxxy.debug.common.service

import android.content.Context
import org.jxxy.debug.corekit.common.CommonServiceManager

interface SocietyService {
    fun goDiscuss(context: Context)
    fun goExcellentClassroom(context: Context)
    fun goExperience(context: Context)
    fun goHistory(context: Context)
    fun goLecture(context: Context)
    fun goMovable(context: Context)
    fun goPlay(context: Context)
    fun goPractice(context: Context)
    fun goRecommend(context: Context)
}

fun goDiscuss(context: Context) {
    CommonServiceManager.service<SocietyService>()?.goDiscuss(context)
}
fun goExcellentClassroom(context: Context) {
    CommonServiceManager.service<SocietyService>()?.goExcellentClassroom(context)
}
fun goExperience(context: Context) {
    CommonServiceManager.service<SocietyService>()?.goExperience(context)
}
fun goHistory(context: Context) {
    CommonServiceManager.service<SocietyService>()?.goHistory(context)
}
fun goLecture(context: Context) {
    CommonServiceManager.service<SocietyService>()?.goLecture(context)
}
fun goMovable(context: Context) {
    CommonServiceManager.service<SocietyService>()?.goMovable(context)
}
fun goPlay(context: Context) {
    CommonServiceManager.service<SocietyService>()?.goPlay(context)
}
fun goPractice(context: Context) {
    CommonServiceManager.service<SocietyService>()?.goPractice(context)
}
fun goRecommend(context: Context) {
    CommonServiceManager.service<SocietyService>()?.goRecommend(context)
}

