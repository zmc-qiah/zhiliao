package org.jxxy.debug.home.Fragment.item.bean

import org.jxxy.debug.home.Fragment.http.response.RecommendType

class DetailItem(
    type: Int?,
    val title: String? = null,
    val author: String? = null,
    val authorHead: String? = null,
    val content: Int? = null,
    val readHot: String? = null,
) :
    RecommendType(type) {
    override fun viewType(): Int {
        return Detail
    }
}