package org.jxxy.debug.test.fragment.activity

import android.view.View
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.test.databinding.FragmentFillTestBinding

class FillTestActivity : BaseActivity<FragmentFillTestBinding>() {
    override fun bindLayout(): FragmentFillTestBinding {
        return FragmentFillTestBinding.inflate(layoutInflater)
    }

    override fun initView() {
        view.btn.singleClick {
            view.run {
                var t = ""
                for (text in fillText.getFillTexts()) {
                    t += text
                    t += ","
                }
                tvFills.text = t.subSequence(0, t.length - 1)
            }
        }
        view.fillText.setText("我是{};，我来自{}。我就是来填个空而已{}")
    }

    override fun subscribeUi() {

    }


}