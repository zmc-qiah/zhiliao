package org.jxxy.debug.test.fragment.bean

import com.google.gson.annotations.SerializedName

class UserInfo() {
    @SerializedName("headPhoto")
    var headPicture:String?=null
    var userName:String?=null
    @SerializedName("token")
    val token:String?= null
    constructor(picture:String?, name:String?):this(){
        this.headPicture = picture
        this.userName = name
    }

    override fun toString(): String {
        return "UserInfo(headPicture=$headPicture, userName=$userName, token=$token)"
    }


}