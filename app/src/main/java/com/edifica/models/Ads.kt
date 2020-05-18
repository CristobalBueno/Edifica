package com.edifica.models

class Ads(
    val userClient: User,
    val usersBusiness: ArrayList<Work>?,
    val settlement: String,
    val province: String,
    val images: ArrayList<String>,
    val formInfo: String
)
