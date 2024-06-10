package org.jxxy.debug.resources.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType
import java.io.Serializable

class ResourceInfo : MultipleType, Serializable {
    var resourceId: Int = 0
    var resourceType: Int = 0
    var resourceLabel: String ? = null
    var resourceTitle: String ? = null
    var resourceAuthorName: String ? = null
    var resourceAuthorHead: String ? = null
    var resourceContent: String ? = null
    var videoUrl: String ? = null
    var resourceReads: Int = 0
    var resourceSearch: Int = 0
    var resourceLikes: Int = 0
    var resourceComments: Int = 0
    var createTime: String ? = null
    override fun toString(): String {
        return "ResourceInfo(resourceId=$resourceId, resourceType=$resourceType, resourceLabel='$resourceLabel', resourceTitle='$resourceTitle', resourceAuthorName='$resourceAuthorName', resourceAuthorHead='$resourceAuthorHead', resourceContent='$resourceContent', resourceReads=$resourceReads, resourceSearch=$resourceSearch, resourceLikes=$resourceLikes, resourceComments=$resourceComments, createTime='$createTime')"
    }

    override fun viewType(): Int = resourceType
}
