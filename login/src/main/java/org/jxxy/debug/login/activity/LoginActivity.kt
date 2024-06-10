package org.jxxy.debug.login.activity

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.http.TokenManager
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.login.R
import org.jxxy.debug.login.databinding.ActivityLoginBinding
import org.jxxy.debug.login.http.viewModel.LoginViewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private val TAG = "LoginActivity"
    val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }
    override fun bindLayout(): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
    var tempPhone: String = ""
    var tempPassword: String = ""
    override fun initView() {
        view.loginBtn.singleClick {
            view.phoneET.clearFocus()
            view.pswET.clearFocus()
            viewModel.login(tempPhone, tempPassword)
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
            startActivity<RegisterActivity>()
            finish()
        }
    }

    override fun subscribeUi() {
        viewModel.loginLiveData.observe(this) {
            it.onSuccess {
                TokenManager.login(it!!.tokenValue)
                viewModel.init(it.tokenValue!!)
                "登录成功".toast()
                lifecycleScope.launch {
                    delay(300)
                    launch(Dispatchers.Main) {
                        val intent = Intent()
                        setResult(Activity.RESULT_OK,intent)
                        finish()
                    }
                }
            }
            it.onError { error, response ->
                error?.message?.apply {
                    toast()
                    if ("账号不存在".equals(this)) {
                        Log.d(TAG, "subscribeUi: " + "账号不存在")
                        view.phoneET.setTextColor(ResourceUtil.getColor(R.color.red))
                        tempPhone = ""
                    } else if ("密码错误".equals((this))) {
                        Log.d(TAG, "subscribeUi: " + "密码错误")
                        view.pswET.setTextColor(ResourceUtil.getColor(R.color.red))
                        tempPassword = ""
                    }
                }
            }
        }
    }

}
