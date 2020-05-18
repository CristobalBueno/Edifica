package com.edifica.models

class User(
    var email: String = "",
    var identifier: Int = 0,
    var name: String = "",
    var phone: String = ""
) {
    override fun toString(): String {
        return "User(email='$email', identifier=$identifier, name='$name', phone='$phone')"
    }
}