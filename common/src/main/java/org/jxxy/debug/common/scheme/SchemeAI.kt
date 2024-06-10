package org.jxxy.debug.common.scheme

class SchemeAI(val aiType:Int = 0,type:Int = Scheme.AI) :Scheme(type) {
    companion object{
        const val EMO = 0
        const val POSE = 1
        const val Guessing = 2
        const val ADV = 3
        const val PAINT = 4
        const val KNOWLEDGE = 5
        const val NAVIGATION = 6
        const val OCR = 7
    }
}