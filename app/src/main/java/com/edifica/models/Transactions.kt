package com.edifica.models

import com.google.firebase.firestore.Exclude

class Transactions (
    @get:Exclude val ad: Ads,
    @get:Exclude val userBusiness: User,
    val price: Int,
    val isAccepted:Boolean
) {
    override fun toString(): String {
        return "Transactions(ad=$ad, userBusiness=$userBusiness, price=$price, isAccepted=$isAccepted)"
    }
}