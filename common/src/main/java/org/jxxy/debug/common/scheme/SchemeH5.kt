package org.jxxy.debug.common.scheme

class SchemeH5(
    type: Int,
    route: String? = null,
    url: String? = null
) : Scheme(type) {
    override fun viewType(): Int {
        return Scheme.H5
    }
}
