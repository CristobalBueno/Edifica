package com.edifica.models

import com.google.firebase.firestore.DocumentId
import java.io.Serializable

class User(
    var email: String = "",
    var identifier: Int = 0,
    var name: String = "",
    var phone: String = "",
    var image: String = "",
    var ratings: ArrayList<Float> = arrayListOf(0F, 0F, 0F, 0F, 0F, 0F),
    var ratingsCount: Int = 0,
    var web: String = "",
    @DocumentId var uid: String = ""
) : Serializable {
    override fun toString(): String {
        return "User(email='$email', identifier=$identifier, name='$name', phone='$phone', image='$image', ratings=$ratings, ratingsCount=$ratingsCount, web='$web')"
    }
}