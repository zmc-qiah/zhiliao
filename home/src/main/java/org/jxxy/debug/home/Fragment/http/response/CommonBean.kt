package org.jxxy.debug.home.Fragment.http.response
import org.jxxy.debug.corekit.recyclerview.MultipleType
abstract class CommonBean():MultipleType {
    constructor( type:Int?,name : String?):this(){
        this.type = type
        this.typeName = name
    }

    var type: Int? = null
    var typeName: String? = ""
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
        const val CAROUSEL_3 =24
        const val NOTICE =25
        const val RECORD = 26
        const val COMPETITION = 27
        const val ADVERTISE = 28
        const val ACTIVITIES = 29
        const val DISCOVERY = 30
        const val THEME = 31
        const val POSTURE = 32
        const val SHARE = 33
        const val COME = 34
        const val POPULARIZE = 35
        const val LEARN_2 = 36
        const val PRACTICE = 37
    }

    override fun toString(): String {
        return "CommonBean(type=$type, name=$)"
    }
}