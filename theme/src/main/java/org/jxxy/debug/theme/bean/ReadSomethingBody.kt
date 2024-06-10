package org.jxxy.debug.theme.bean

import com.google.gson.annotations.SerializedName

class ReadSomethingBody(@SerializedName("url") val url : String, @SerializedName("baike_num") val num : Int) {
}