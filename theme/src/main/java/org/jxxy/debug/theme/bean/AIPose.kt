package org.jxxy.debug.theme.bean

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.theme.MyType

class AIPose(
    name: String,
    photo: String,
    scheme: Scheme?
):AIBean(name, photo, scheme, MyType.AI_PAINT) {
    override fun viewType(): Int {
        return MyType.AI_POSE
    }
}