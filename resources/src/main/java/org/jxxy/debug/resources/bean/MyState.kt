package org.jxxy.debug.resources.bean

import java.io.Serializable

class MyState : Serializable {
    var likeState: Int = 0
    var collectState: Int = 0

    constructor()

    override fun toString(): String {
        return "MyState(likeState=$likeState, collectState=$collectState)"
    }
}
