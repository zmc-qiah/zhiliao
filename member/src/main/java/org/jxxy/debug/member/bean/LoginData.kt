package org.jxxy.debug.member.bean

class LoginData(
    val isLogin: Boolean?,
    val loginDevice: String?,
    val loginId: String?,
    val loginType: String?,
    val sessionTimeout: Long?,
    val tag: String? = null,
    val tokenActiveTimeout: Long?,
    val tokenName: String?,
    val tokenSessionTimeout: Long?,
    val tokenTimeout: Long?,
    val tokenValue: String?
) {
    constructor() : this(null, null, null, null, null, null, null, null, null, null, null)

    override fun toString(): String {
        return "LoginData(isLogin=$isLogin, loginDevice=$loginDevice, loginId=$loginId, loginType=$loginType, sessionTimeout=$sessionTimeout, tag=$tag, tokenActiveTimeout=$tokenActiveTimeout, tokenName=$tokenName, tokenSessionTimeout=$tokenSessionTimeout, tokenTimeout=$tokenTimeout, tokenValue=$tokenValue)"
    }
}
