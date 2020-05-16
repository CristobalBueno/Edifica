package com.edifica.models

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class Token(
    var name: String,
    var phone: String,
    var email: String,
    var password: String,
    var uid: String,
    var identifier: Int
) {

    fun saveToken(file: File) {
        var fileOut = FileOutputStream(file)

        fileOut.write(("$name,$phone,$email,$uid,$identifier").toByteArray())
        fileOut.close()
    }

    override fun toString(): String {
        return "Token(name='$name', phone='$phone', email='$email', password='$password', uid='$uid', identifier=$identifier)"
    }


    companion object {
        fun readToken(file: File): Token {

            var fileOut = FileInputStream(file)

            var text = fileOut.readBytes().toString(charset("UTF-8"))
            var name = text.split(",")

            var token = Token(name.get(0), name.get(1), name.get(2), "", name.get(3), name.get(4).toInt())
            fileOut.close()

            return token
        }

        fun deleteToken(file: File) {
            file.delete()
        }
    }


}
