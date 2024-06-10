package org.jxxy.debug.member.bean

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.corekit.recyclerview.MultipleType

class MarkResource(
var title: String? = null,
var author: String? = null,
var authorHead: String? = null,
var content: String? = null,
var photo: String? = null,
var type: Int = -1,
var reads: Int = -1,
var likes: Int = -1,
var createTime:String? = null,
var scheme: Scheme? = null
) : MultipleType {
    override fun viewType(): Int = type

    constructor() : this(null)
}