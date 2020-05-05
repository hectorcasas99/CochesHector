package com.hector.cocheshector.model

import java.io.Serializable

data class Usuario(
    val nombre: String,
    val nick: String,
    val email: String,
    val foto: String
)

data class Vehiculo(
    val marca: String,
    val modelo: String,
    val color: String,
    val anno: String,
    val carroceria: String,
    val km: String,
    val precio: String,
    val fotos: ArrayList<String>,
    val idUser: String
)