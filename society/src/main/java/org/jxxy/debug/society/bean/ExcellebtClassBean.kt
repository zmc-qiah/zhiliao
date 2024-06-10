package org.jxxy.debug.society.bean
import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.common.scheme.SchemeDetail
import org.jxxy.debug.corekit.recyclerview.MultipleType

class ExcellebtClassBean (val type :Int,val text :String,val image:String,val scheme: SchemeDetail) :
    MultipleType {
    override fun viewType(): Int {
        return type
    }
}