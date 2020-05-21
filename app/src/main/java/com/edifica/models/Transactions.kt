package com.edifica.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName

class Transactions (
    @get:Exclude var ad: Ads = Ads(),
    @get:Exclude var userBusiness: User = User(),
    @PropertyName ("price") var price: Float = 0F,
    @get:PropertyName ("isAccepted") var isAccepted:Boolean = false,
    @get:DocumentId var id: String = ""
) {
    override fun toString(): String {
        return "Transactions(ad=$ad, userBusiness=$userBusiness, price=$price, isAccepted=$isAccepted, id='$id')"
    }
}