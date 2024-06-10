package org.jxxy.debug.theme.floatball.utils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
object ViewModleMain : ViewModel() {
    val isShowSuspendWindow = MutableLiveData<Boolean>()
    val stateModel : MutableLiveData<Boolean> = MutableLiveData()
    val isVisible = MutableLiveData<Boolean>()
    val isWakeUp = MutableLiveData<Boolean>()
}
