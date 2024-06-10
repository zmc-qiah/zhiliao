package org.jxxy.debug.common.util

import androidx.lifecycle.MutableLiveData

class IdentityUtil private constructor(){
    companion object{
        val instance :IdentityUtil by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
            IdentityUtil()
        }
    }

    val state = MutableLiveData<Int>()
}