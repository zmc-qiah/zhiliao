package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.GridFourBean

class GridFour(
    type: Int?,name : String?,
    val grid4Infos: List<GridFourBean>? = null
) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return GRIDFOUR
    }
}
