package org.jxxy.debug.login.bean

class LoginData() {
    var password: String? = null
    var mobile: String? = null
    constructor(mobile: String, password: String) : this() {
        this.password = password
        this.mobile = mobile
    }

    override fun toString(): String {
        return "loginData(password=$password, mobile=$mobile)"
    }
}
