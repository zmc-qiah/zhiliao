package org.jxxy.debug.theme.bean

import com.google.gson.annotations.SerializedName

data class Baike(@SerializedName("baike_url") val baiKeUrl: String,@SerializedName("image_url") val imageUrl : String,val description : String,) {
}


