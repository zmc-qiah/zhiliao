package org.jxxy.debug.common.bean

class ResourceRespone(
    val userPhone: String? = null,
    val collectionNums: Int? = null,
    val list: List<Resource> ? = null
) {
    constructor() : this(null)
    override fun toString(): String {
        return "ResourceRespone(userPhone=$userPhone, collectionNums=$collectionNums, list=$list)"
    }
}
