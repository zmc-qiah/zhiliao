package org.jxxy.debug.login.http.viewModel

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jxxy.debug.common.http.BaseLiveDataCallback2
import org.jxxy.debug.corekit.http.BaseViewModel
import org.jxxy.debug.corekit.http.bean.ResLiveData
import org.jxxy.debug.corekit.http.bean.Resource
import org.jxxy.debug.corekit.http.request
import org.jxxy.debug.login.http.repository.LoginRepository
import org.jxxy.debug.login.http.respone.LoginRespone

class LoginViewModel(app: Application) : BaseViewModel(app) {
    companion object {
        var FLAG = 0
    }
    val repository: LoginRepository by lazy { LoginRepository() }
    val loginLiveData: ResLiveData<LoginRespone?> by lazy { ResLiveData() }
    val registerLiveData: ResLiveData<LoginRespone> by lazy { ResLiveData() }
    fun login(usePhone: String, password: String) {
        val run = object : Runnable {
            override fun run() {
                TODO("Not yet implemented")
            }
        }
        Thread(run).start()
        run.run()
        viewModelScope.launch {
            val login = repository.login(usePhone, password)
            login?.data?.let {
                loginLiveData.postValue(Resource.success(it))
            }
        }
        request(
            loginLiveData,
            object : BaseLiveDataCallback2<LoginRespone?> {}
        ) {
            val login = repository.login(usePhone, password)
            login
        }
    }
    fun register(usePhone: String, password: String) {
        request(
            registerLiveData,
            object : BaseLiveDataCallback2<LoginRespone> {}
        ) {
            repository.register(usePhone, password)
        }
    }
    fun init(value: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.init(value)
        }
    }
}
