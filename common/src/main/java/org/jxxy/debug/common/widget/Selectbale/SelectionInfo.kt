package com.debug.myapplication.Selectbale

import java.io.Serializable

class SelectionInfo : Cloneable, Serializable {
    var start = 0
    var end = 0
    var selectionContent: String? = null

    @Throws(CloneNotSupportedException::class)
    public override fun clone(): Any {
        return super.clone()
    }
}
