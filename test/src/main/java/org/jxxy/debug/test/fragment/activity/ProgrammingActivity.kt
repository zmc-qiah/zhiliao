package org.jxxy.debug.test.fragment.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.test.R
import org.jxxy.debug.test.databinding.ActivityProgrammingBinding

class ProgrammingActivity : BaseActivity<ActivityProgrammingBinding>(){
    override fun bindLayout(): ActivityProgrammingBinding {
        return ActivityProgrammingBinding.inflate(layoutInflater)
    }

    override fun initView() {

    }

    override fun subscribeUi() {

    }
}