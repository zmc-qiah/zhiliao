package org.jxxy.debug.society.activity

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.society.adapter.LegalAdapter
import org.jxxy.debug.society.databinding.ActivityLegalBinding

class LegalActivity: BaseActivity<ActivityLegalBinding>() {
    override fun bindLayout(): ActivityLegalBinding {
        return ActivityLegalBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
        view.legalRv.layoutManager=layoutManager
        val adapter= LegalAdapter ()
        view.legalRv.adapter=adapter
    }



    override fun subscribeUi() {

    }
}