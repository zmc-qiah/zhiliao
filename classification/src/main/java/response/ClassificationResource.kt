package response

import org.jxxy.debug.common.scheme.Scheme


class ClassificationResource(
    val title: String,
    val type : Int,
    val author: String,
    val authorHead: String,
    val createTime: String,
    val describe: String,
    val ip: String,
    val likes: Int,
    val photo: String,
    val readHot: Int,
    val scheme: Scheme
) {
}