package org.jxxy.debug.resources.http.response

import org.jxxy.debug.corekit.recyclerview.MultipleType
import org.jxxy.debug.resources.bean.MyState
import org.jxxy.debug.resources.bean.ResourceInfo
import org.jxxy.debug.resources.bean.TagInfo
import java.io.Serializable

class ResourceInfoResponse : MultipleType, Serializable {
    var resourceInfo: ResourceInfo ? = null
    var photosUrl: ArrayList<String> ?= null
    var myState: MyState ? = null
    var type: Int = -1
    var tagInfo:List<TagInfo>?=null

    override fun toString(): String {
        return "Data(resourceInfo=$resourceInfo, photosUrl=$photosUrl, myState=$myState),$tagInfo"
    }

    override fun viewType(): Int = type
}
