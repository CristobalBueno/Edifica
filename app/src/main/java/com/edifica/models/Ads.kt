package com.edifica.models

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import java.io.Serializable

class Ads(
    @get:Exclude @set:PropertyName("user") var user: User = User(),
    var settlement: String = "",
    var province: String = "",
    var images: ArrayList<String> = arrayListOf(),
    var formInfo: String = "",
    var guilds: List<String> = listOf()
) : Serializable {
    override fun toString(): String {
        return "Ads(user=$user, settlement='$settlement', province='$province', images=$images, formInfo='$formInfo', guilds=${guilds})"
    }
}
