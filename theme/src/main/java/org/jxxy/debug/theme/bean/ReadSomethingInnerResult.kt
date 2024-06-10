package org.jxxy.debug.theme.bean

import com.google.gson.annotations.SerializedName
import org.jxxy.debug.corekit.recyclerview.MultipleType

data class ReadSomethingInnerResult (
    val keyword: String,
    val root: String,
    val score: Double,
    @SerializedName("baike_info") val baike : Baike? = null
) : MultipleType{
    override fun viewType(): Int {
        if(score != 0.toDouble()){
            return 1
        }else{
            return 2
        }
    }
}