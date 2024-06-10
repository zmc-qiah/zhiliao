package org.jxxy.debug.home.Fragment.http.response

import org.jxxy.debug.corekit.recyclerview.MultipleType

abstract class RecommendType(val type: Int?) : MultipleType {
    companion object {
        const val Detail = 1
    }
}