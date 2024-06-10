package org.jxxy.debug.home.Fragment.http.response

import org.jxxy.debug.corekit.recyclerview.MultipleType

abstract class Study (type: Int?) :MultipleType {
    companion object {
        const val DAILY = 14
        const val TOOLS = 15
        const val COURSE = 16
        const val BOOK = 17
        const val INTERVIEW = 18
        const val TECHNOLOGY = 19
        const val LEARN = 20
        const val BREAKTHOUGH = 21
        const val READ = 22
        const val TED = 23
        const val LEARN_2 = 36
    }

}
