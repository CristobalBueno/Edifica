package com.edifica.models

import android.net.Uri

object Dataholder {
    val FILENAME = "auth"

    val spinnerSettlement = arrayOf(
        "Trabajo a realizar",
        "Vivienda",
        "Restaurante",
        "Local",
        "Hotel"
    )

    val spinnerProvince = arrayOf(
        "Provincia",
        "Málaga",
        "Sevilla",
        "Cádiz",
        "Huelva",
        "Granada",
        "Córdoba",
        "Almería",
        "Jaén"
    )

    // TODO ESTO DEBE DE SACARSE DE BASE DE DATOS (O DE MEMORIA)
    var name = ""
    var phone = ""
    var email = ""
    var photo: Uri? = null

    // TODO ESTO DEBE DE SACARSE DE BASE DE DATOS (O DE MEMORIA)
    lateinit var offersentbusiness: String
}