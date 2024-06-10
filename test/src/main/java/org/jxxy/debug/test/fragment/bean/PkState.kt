package org.jxxy.debug.test.fragment.bean

class PkState() {
    var userInfo:UserInfo?=null
    var point:Int = 0
    var isEnd = false
    var postion = -1
    constructor(userInfo: UserInfo?,point:Int,isEnd:Boolean,postion:Int):this(){
        this.userInfo = userInfo
        this.point = point
        this.isEnd = isEnd
        this.postion = postion
    }
}