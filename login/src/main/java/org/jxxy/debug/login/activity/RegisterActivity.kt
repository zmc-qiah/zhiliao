package org.jxxy.debug.login.activity

import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.http.TokenManager
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.login.R
import org.jxxy.debug.login.databinding.ActivityLoginBinding
import org.jxxy.debug.login.http.viewModel.LoginViewModel

class RegisterActivity : BaseActivity<ActivityLoginBinding>() {
    val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }
    override fun bindLayout(): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
    var tempPhone: String = ""
    var tempPassword: String = ""
    override fun initView() {
        view.apply {
            loginToolbar.setTitleText("注册账号")
            bigTextTV.text = "注册发现更多精彩"
            loginBtn.text = "注册"
            smellTV1.text = "已有账号？"
            smellTV2.text = "去登录"
        }
        view.loginBtn.singleClick {
            viewModel.register(tempPhone, tempPassword)
        }
        view.phoneET.setOnFocusChangeListener { ET, focused ->
            val view = ET as EditText
            if (!focused) {
                tempPhone = view.text.toString()
            } else {
                view.setTextColor(ResourceUtil.getColor(R.color.black))
                view.setText(tempPhone)
            }
        }
        view.pswET.setOnFocusChangeListener { ET, focused ->
            val view = ET as EditText
            if (!focused) {
                tempPassword = view.text.toString()
            } else {
                view.setTextColor(ResourceUtil.getColor(R.color.black))
                view.setText(tempPassword)
            }
        }
        view.smellTV2.singleClick {
            startActivity<LoginActivity>()
            finish()
        }
    }

    override fun subscribeUi() {
        viewModel.registerLiveData.observe(this) {
            it.onSuccess {
                "注册成功".toast()
                TokenManager.login(it!!.tokenValue)
                viewModel.init(it.tokenValue!!)
                finish()
            }.onError { error, response ->
                error?.message.toast()
            }
        }
        viewModel.loginLiveData.observe(this) {
            it.onSuccess {
                TokenManager.login(it!!.tokenValue)
                finish()
            }
        }
    }
}
