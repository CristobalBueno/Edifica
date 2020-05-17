package com.practica.proyect_no_name.Interface

import com.edifica.models.Business

interface CustomItemListener {
    fun onItemClick(currentBusiness: Business, position: Int)
}