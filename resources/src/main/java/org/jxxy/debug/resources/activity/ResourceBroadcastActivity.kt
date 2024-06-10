package org.jxxy.debug.resources.activity

import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.resources.databinding.ActivityBroadcastBinding

class ResourceBroadcastActivity:BaseActivity<ActivityBroadcastBinding>() {
    override fun bindLayout(): ActivityBroadcastBinding = ActivityBroadcastBinding.inflate(layoutInflater)

    override fun initView() {
    }

    override fun subscribeUi() {
    }
}