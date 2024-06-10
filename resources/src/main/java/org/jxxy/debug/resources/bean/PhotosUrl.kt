package org.jxxy.debug.resources.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType
import org.jxxy.debug.resources.util.Mytype

data class PhotosUrl(val photosUrl: ArrayList<String> = ArrayList()) : MultipleType {
    override fun viewType(): Int = Mytype.RESOURCE_PHOTOS
}
