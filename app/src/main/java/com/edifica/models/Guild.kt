package com.edifica.models

import android.graphics.drawable.Drawable

class Guild(val guildName: String, val drawable: Drawable, var isChecked: Boolean) {

    // Image = Drawable name
    enum class GuildName(val guildName: String, val image: String) {
        carpenter("Carpinteros", "carpintero"), painter("Pintores", "pintor"),
        builder("Albañiles", "albanil"), electrician("Electricistas", "electricidad"),
        plumber("Fontaneros", "fontanero"), plasterer("Escayolistas", "escayola"),
        metalic_carpenter("Carpintería metálica", "metalica")
    }

}