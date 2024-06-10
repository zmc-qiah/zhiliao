package orgjxxy.debug.identity.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class IdentityTitleBean(val type: Int, val text: String) :
    MultipleType {
    override fun viewType(): Int {
        return type
    }
}