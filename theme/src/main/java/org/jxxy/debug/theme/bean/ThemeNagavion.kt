package org.jxxy.debug.theme.bean

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.theme.MyType

class ThemeNavigation(
    val list: List<ThemeNavigationBean>,
    name: String,
    photo: String,
    scheme: Scheme?
) :AIBean(
    name, photo, scheme, MyType.AI_NAVIGATION
) {
    override fun viewType(): Int {
        return MyType.AI_NAVIGATION
    }
}
data class ThemeNavigationBean(
    val name:String,
    val url:String,
    val photo:String,
    val content: String
)