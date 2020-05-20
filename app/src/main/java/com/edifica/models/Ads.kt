package com.edifica.models

import com.google.firebase.firestore.Exclude
import java.io.Serializable

class Ads(
    @get:Exclude var user: User = User(),
    var settlement: String = "",
    var province: String = "",
    var images: ArrayList<String> = arrayListOf(),
    var formInfo: String = "",
    var guilds: Array<String> = arrayOf()
) : Serializable {
    override fun toString(): String {
        return "Ads(user=$user, settlement='$settlement', province='$province', images=$images, formInfo='$formInfo', guilds=${guilds.contentToString()})"
    }
}
