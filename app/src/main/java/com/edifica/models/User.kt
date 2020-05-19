package com.edifica.models

import com.google.firebase.database.PropertyName

class User(
    @PropertyName("email") var email: String = "",
    @PropertyName("identifier") var identifier: Int = 0,
    @PropertyName("name") var name: String = "",
    @PropertyName("phone") var phone: String = "",
    @PropertyName("image") var image: String = "",
    @PropertyName("rating") var ratings: ArrayList<Float> = arrayListOf(0F, 0F, 0F, 0F, 0F, 0F),
    @PropertyName("web") var web: String = ""
) {
    override fun toString(): String {
        return "User(email='$email', identifier=$identifier, name='$name', phone='$phone', image='$image', ratings=$ratings, web='$web')"
    }
}