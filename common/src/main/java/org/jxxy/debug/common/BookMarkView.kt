package org.jxxy.debug.common

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import navigation
import org.jxxy.debug.common.databinding.FramelayoutBookMarkBinding
import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick

class BookMarkView(context: Context, marginTop: Int, content: String = "", photoUrl: String = "") :
    FrameLayout(context) {
    var count = 0
    var view: FramelayoutBookMarkBinding

    init {
        view = FramelayoutBookMarkBinding.inflate(LayoutInflater.from(context), this, true)
        val params = FrameLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.TOP
        params.topMargin = marginTop
        view.root.layoutParams = params
//        view.bookMarkTv.text = "AI\n绘画"

    }

    fun setData(scheme: Scheme, photoUrl: String = "", name: String = "") {
        view.bookMarkBackIv.load(
            "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/10/eeeb0543-a635-4fd5-b0c7-12c5ee93cd41.png",
            2
        )
        if (photoUrl?.isEmpty() ?: true) {
            view.bookMarkIv.load(
                "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/15/406be06b-d745-45f1-a9e2-ea9ee58143ec.png",
                2
            )
            Log.d("PhotoUrl", "我进入了")
        } else {
            view.bookMarkIv.load(photoUrl, 1)
        }
        if (!name.isEmpty()) {
            view.bookMarkTv.text = name
        }

        view.bookMarkIv.singleClick {
            scheme?.navigation(context)
        }

    }
}