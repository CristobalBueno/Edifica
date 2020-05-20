package com.edifica.models

import com.google.firebase.firestore.Exclude

class Transactions (
    @get:Exclude var ad: Ads = Ads(),
    @get:Exclude var userBusiness: User = User(),
    var price: Float = 0F,
    var isAccepted:Boolean = false
) {
    override fun toString(): String {
        return "Transactions(ad=$ad, userBusiness=$userBusiness, price=$price, isAccepted=$isAccepted)"
    }
}