package cn.lentme.projects.whitetile.core

const val GameFrameWidth = 720
const val GameFrameHeight = 1080

const val BaseTextSize = 16f

const val GameFPS = 60
const val deltaTimeUnit = (1f/GameFPS.toFloat() * 1000f)
const val UiSleep = deltaTimeUnit.toLong()

const val InitSpeed = 0.8f
const val SpeedUpCounter = 10
const val SpeedUpUnit = 0.18f

