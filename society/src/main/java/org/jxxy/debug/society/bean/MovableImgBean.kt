package org.jxxy.debug.society.bean
import org.jxxy.debug.common.scheme.SchemeH5


class MovableImgBean(val image:String,val scheme :SchemeH5) {
    fun getValue(): String {
        return image
    }

}