package org.jxxy.debug.home.Fragment.http.response

import org.jxxy.debug.corekit.recyclerview.MultipleType

abstract class Component(type: Int?) :MultipleType {
    companion object {
        const val CAROUSEL = 1
        const val HOTTOPIC = 2
        const val LAST =3
        const val VOTE = 4
        const val History =5
        const val EXPERT =6
        const val LIKE = 7
        const val NEWS=8
        const val LAW =9
        const val FLASH =10
        const val GRID=11
        const val GRIDFOUR = 12
        const val FALL=13
        const val POPULARIZE = 35
    }
}
