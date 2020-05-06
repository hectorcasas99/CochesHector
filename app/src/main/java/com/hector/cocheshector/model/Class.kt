package com.hector.cocheshector.model

import java.io.Serializable

data class Usuario(
    val nombre: String,
    val nick: String,
    val email: String,
    val foto: String=""
):Serializable

data class Vehiculo(
    val marca: String?=null,
    val modelo: String?=null,
    val color: String?=null,
    val anno: String?=null,
    val carroceria: String?=null,
    val caballos: String?=null,
    val km: String?=null,
    val precio: String?=null,
    val fotos: ArrayList<String>,
    val iduser: String?=null
):Serializable