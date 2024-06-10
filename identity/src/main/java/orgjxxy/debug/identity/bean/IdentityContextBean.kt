package orgjxxy.debug.identity.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class IdentityContextBean (val type: Int, val opention: String, val context: String) :
    MultipleType {
    override fun viewType(): Int {
        return type
    }
}