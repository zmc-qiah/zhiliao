package org.jxxy.debug.theme.voice

import androidx.lifecycle.MutableLiveData

class VoiceLiveData private constructor() {
    companion object{
        val instance : VoiceLiveData by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
            VoiceLiveData()
        }
    }
    val OPEN_LISTENER = "!@#$%^&*()"
    val isWeak = MutableLiveData<Boolean>()
    val sayText = MutableLiveData<String>()
    val listenText = MutableLiveData<String>()
    val isSay = MutableLiveData<Boolean>()
}