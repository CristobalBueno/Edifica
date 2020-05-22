package com.edifica.models

import com.google.firebase.firestore.Exclude

class Chat(
    @get:Exclude var business: User = User(),
    @get:Exclude var user: User = User(),
    var messages: ArrayList<HashMap<String, String>> = arrayListOf()
) {

    override fun toString(): String {
        return "Chat(business=$business, user=$user, messages=$messages)"
    }
}