package orgjxxy.debug.identity.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class IdentityChooseBean (val type: Int, val img: String, val context: String,val identity:Int) :
    MultipleType {
    override fun viewType(): Int {
        return type
    }
}