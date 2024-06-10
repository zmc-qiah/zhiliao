package org.jxxy.debug.search.activity

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import androidx.core.content.ContextCompat
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.search.R
import org.jxxy.debug.search.databinding.ActivitySearchEndBinding
import org.jxxy.debug.search.databinding.ActivityWikiBinding

class WikiActivity : BaseActivity<ActivityWikiBinding>() {


    override fun bindLayout(): ActivityWikiBinding {
        return ActivityWikiBinding.inflate(layoutInflater)
    }

    override fun initView() {

    }

    override fun subscribeUi() {

    }
}