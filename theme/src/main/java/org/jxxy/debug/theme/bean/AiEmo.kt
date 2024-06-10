package org.jxxy.debug.theme.bean

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.theme.MyType

class AiEmo(
    val list: List<AiEmoBean>,
    name: String,
    photo: String,
    scheme: Scheme?
):
    AIBean(name, photo, scheme, MyType.AI_EMO) {
}
data class AiEmoBean(
    val data: String,
    val content: String,
    val photo: String,
    val icon:String,
)