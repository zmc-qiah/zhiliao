package org.jxxy.debug.test.fragment.myListener

import android.view.View
import org.jxxy.debug.test.fragment.bean.PkChoose

interface ItemClickListener {
    fun onItemClick(view: View, position: Int,entity: PkChoose)
}