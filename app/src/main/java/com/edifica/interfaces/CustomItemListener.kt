package com.practica.proyect_no_name.Interface

import com.edifica.models.User

interface CustomItemListener {
    fun onItemClick(currentUser: User, position: Int)
}