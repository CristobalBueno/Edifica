package com.edifica.models

import java.io.Serializable

class Business (var name: String = "", var phone: String= "", var email: String= "", var image: String= "", var ratings: ArrayList<Float> = arrayListOf(), var web: String= "") : Serializable
