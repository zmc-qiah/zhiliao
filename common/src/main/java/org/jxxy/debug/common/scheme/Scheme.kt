package org.jxxy.debug.common.scheme

import com.google.gson.annotations.SerializedName
import org.jxxy.debug.corekit.recyclerview.MultipleType

open class Scheme(val type: Int = -1) : MultipleType {
    var route: String? = null
    var resourceId: Int? = null
    var url: String? = null
    var keyword: String? = null
    @SerializedName("mapId")
    var societyType :Int? =null
    constructor() : this(-1)
    companion object {
        const val H5 = 1
        const val SEARCH = 2
        const val DETAIL = 3
        const val Video = 4
        const val Society=5
        const val Plan = 6
        const val Text = 8
        const val PAINT = 7
        const val GAME = 9
        const val EMO = 10
        const val AI = 11
    }

    override fun viewType(): Int = type
}
