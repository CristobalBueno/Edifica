package com.edifica.models

import com.google.firebase.database.PropertyName
import com.google.firebase.firestore.DocumentId
import java.io.Serializable

class User(
    @PropertyName("email") var email: String = "",
    @PropertyName("identifier") var identifier: Int = 0,
    @PropertyName("name") var name: String = "",
    @PropertyName("phone") var phone: String = "",
    @PropertyName("image") var image: String = "",
    @PropertyName("rating") var ratings: ArrayList<Float> = arrayListOf(0F, 0F, 0F, 0F, 0F, 0F),
    @PropertyName("ratingsCount") var ratingsCount: Int = 0,
    @PropertyName("web") var web: String = "",
    @DocumentId var uid: String = ""
) : Serializable {
    override fun toString(): String {
        return "User(email='$email', identifier=$identifier, name='$name', phone='$phone', image='$image', ratings=$ratings, ratingsCount=$ratingsCount, web='$web')"
    }
}