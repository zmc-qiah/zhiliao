package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.GridBean

class Grid (type: Int?,name : String?, val grid4Infos: List<GridBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return GRID
    }


    override fun toString(): String {
        return "Grid(grid4Infos=$grid4Infos)"
    }

}
