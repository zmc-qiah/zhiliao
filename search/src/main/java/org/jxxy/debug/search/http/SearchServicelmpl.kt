package org.jxxy.debug.search.http

import android.content.Context
import android.content.Intent
import com.google.auto.service.AutoService
import org.jxxy.debug.common.service.SearchEndService
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.search.activity.SearchEndActivity
import org.jxxy.debug.search.activity.SearchMiddleActivity


@AutoService(SearchEndService::class)
class SearchServicelmpl:SearchEndService {
    override fun goSearchEnd(context: Context, keyword: String) {
        val intent = Intent(context, SearchEndActivity::class.java)
        intent.putExtra("keyword", keyword)
        context.startActivity(intent)
    }

    override fun goSearchEnd(context: Context, flag: Int, keyword: String) {
        val intent = Intent(context, SearchEndActivity::class.java)
        intent.putExtra("keyword", keyword)
        intent.addFlags(flag)
        context.startActivity(intent)
    }

    override fun gonSearchMiddle(context: Context) {
        context.startActivity<SearchMiddleActivity>()
    }

}


