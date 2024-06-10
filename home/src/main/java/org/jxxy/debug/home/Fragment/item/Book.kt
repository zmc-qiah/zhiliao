package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.BookBean

class Book (type: Int?,name : String?, val aiBooks: List<BookBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return BOOK
    }
}