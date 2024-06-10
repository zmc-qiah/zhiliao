package org.jxxy.debug.theme.adapter

import org.jxxy.debug.common.scheme.Scheme

interface ItemClickListener {
    fun onClick(position: Int,scheme: Scheme?)
}
