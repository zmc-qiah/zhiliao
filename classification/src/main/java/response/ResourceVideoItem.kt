package org.jxxy.debug.common.http.Response

class ResourceVideoItem(
    val resourceId: Int? = null,
    type: Int?,
    val title: String? = null,
    // 资源缩略图
    val titlePhoto: String? = null,
    // 资源描述
    val describe: String? = null,
    // 资源阅读量（点击量）
    val readNumber: String? = null,
    // 资源点赞量
    val likeNumber: String? = null,
    // 发布时间
    val createTime: String? = null,
    // 作者名称
    val authorName: String? = null,
    // 作者头像
    val authorPhoto: String? = null
) : ResourceType(type) {
    override fun viewType(): Int {
        return VIDEO
    }
}
