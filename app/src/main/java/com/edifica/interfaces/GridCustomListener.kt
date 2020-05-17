package com.practica.proyect_no_name.Interface

import com.edifica.models.Guild


interface GridCustomListener {
    fun onItemClick(guild: Guild, position: Int)
}