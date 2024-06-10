package org.jxxy.debug.common.http.Response

import org.jxxy.debug.corekit.recyclerview.MultipleType

abstract class ResourceType(val type: Int?) : MultipleType {
    companion object {
        // 资源类型 1文章 2图片 3视频 4法律卡片
        const val TEXT = 1
        const val PICTURE = 2
        const val VIDEO = 3
    }
}
