package org.jxxy.debug.theme

import org.jxxy.debug.corekit.recyclerview.MultipleType

interface MyType:MultipleType {
    companion object{
        val CHAT_USER = 1011
        val CHAT_ASSISTANT = 1012
        val CHAT_TRANSLATE= 1013
        val AI_PAINT= 1014
        val AI_NAVIGATION= 1015
        val AI_POSE = 1016
        val AI_EMO = 1017
        val AI_OCR = 1018
        val AI_ADV= 1019
        val AI_KNOWLEDGE = 1020
        val AI_GUESS_GAME = 1021

    }
}