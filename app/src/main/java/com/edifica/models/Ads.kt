package com.edifica.models

import com.google.firebase.firestore.Exclude

class Ads (@get:Exclude var user: User = User(), var settlement: String= "", var province: String= "", var images: ArrayList<String> = arrayListOf(), var formularyInfo: String = "")
