package org.jxxy.debug.common.scheme

class SchemeSearch(
    type: Int,
    route: String? = null,
    keyword: String? = null
) : Scheme(type) {
    override fun viewType(): Int {
        return Scheme.SEARCH
    }
}
