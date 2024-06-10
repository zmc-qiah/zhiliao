package org.jxxy.debug.thinkMap.bean

import android.graphics.drawable.Drawable
import com.google.gson.annotations.SerializedName
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.thinkMap.R

data class UserInfo(
    @SerializedName("headPhoto")
    var headPicture: String? = null,
    var userName: String? = null,
    @SerializedName("userToken")
    var token: String? = null,
    @Transient
    var position: Int = 0,
    var mapId: Int = 0
){
    companion object{
        val userColorList = listOf<Drawable>(
            ResourceUtil.getDrawable(org.jxxy.debug.common.R.drawable.round_p_200)!!,
            ResourceUtil.getDrawable(R.drawable.round_orange)!!,
            ResourceUtil.getDrawable(R.drawable.round_purple)!!,
            ResourceUtil.getDrawable(R.drawable.round_pink)!!,
            ResourceUtil.getDrawable(R.drawable.round_yellow)!!,
        )
    }

    override fun toString(): String {
        return "UserInfo(headPicture=$headPicture, userName=$userName, token=$token)"
    }


}