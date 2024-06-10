package org.jxxy.debug.common.scheme

class SchemeDetail(
    type: Int,
    route: String? = null, // 路线
    resourceId: Int? = null
) : Scheme(type) {
    override fun viewType(): Int {
        return Scheme.DETAIL
    }
}
