package org.jxxy.debug.test.fragment.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.test.R
import org.jxxy.debug.test.databinding.ActivityGameBinding

class GameActivity : BaseActivity<ActivityGameBinding>() {
    override fun bindLayout(): ActivityGameBinding {
        return ActivityGameBinding.inflate(layoutInflater)
    }

    override fun initView() {

    }

    override fun subscribeUi() {

    }
}