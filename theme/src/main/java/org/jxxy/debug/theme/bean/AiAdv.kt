package org.jxxy.debug.theme.bean

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.theme.MyType
import org.opencv.photo.Photo

class AiAdv(
val list: List<AiAdvBean>,
name: String,
photo: String,
scheme: Scheme?
): AIBean(name, photo, scheme, MyType.AI_ADV) {
    override fun viewType(): Int = MyType.AI_ADV
}
data class AiAdvBean(
    val name: String,
    val photo: String)