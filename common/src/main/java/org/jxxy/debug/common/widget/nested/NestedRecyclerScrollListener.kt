package org.jxxy.debug.common.widget.nested

import androidx.recyclerview.widget.RecyclerView

interface NestedRecyclerScrollListener {

    fun onRecyclerScroll(parent: RecyclerView, totalScrollY: Int)
}
