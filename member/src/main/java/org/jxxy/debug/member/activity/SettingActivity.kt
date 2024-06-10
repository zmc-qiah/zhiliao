package org.jxxy.debug.member.activity

import androidx.lifecycle.ViewModelProvider
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.http.TokenManager
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.member.databinding.ActivitySettingBinding
import org.jxxy.debug.member.http.viewModel.MemberViewModel

class SettingActivity : BaseActivity<ActivitySettingBinding>() {
    val viewModel: MemberViewModel by lazy {
        ViewModelProvider(this).get(MemberViewModel::class.java)
    }
    override fun bindLayout(): ActivitySettingBinding = ActivitySettingBinding.inflate(layoutInflater)

    override fun initView() {
        view.loginOutBtn.singleClick {
            viewModel.logout()
            finish()
            TokenManager.loginOut()
        }
    }

    override fun subscribeUi() {
    }
}
