package org.jxxy.debug.member.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class SettingItem(val type: Int, val name: String? = null, val date: String ? = null) : MultipleType{
    override fun viewType(): Int = type
}
