package org.jxxy.debug.resources.bean

class MyPage {
    var current: Int = -1
    var pages: Int = -1
    var size: Int = -1
    var total: Int = -1

    override fun toString(): String {
        return "MyPage(current=$current, pages=$pages, size=$size, total=$total)"
    }
}
