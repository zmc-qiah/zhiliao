package org.jxxy.debug.theme.bean

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.theme.MyType

class ThemeAIPaint(
    val list: List<ThemeAIPaintBean>,
    name: String,
    photo: String,
    scheme: Scheme?
): AIBean(name, photo, scheme, MyType.AI_PAINT) {
    override fun viewType(): Int = MyType.AI_PAINT
}
data class ThemeAIPaintBean(
    val tag: String,
    val photo: String
)