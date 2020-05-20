package com.edifica.models

class Transactions (
    val ad: Ads,
    val userBusiness: User,
    val userClient:User,
    val price: Int,
    val isAccepted:Boolean
)