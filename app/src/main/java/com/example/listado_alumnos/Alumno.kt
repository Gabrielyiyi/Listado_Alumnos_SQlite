package com.example.listado_alumnos

data class Alumno(
    val id: Int = 0,
    val nombre: String,
    val cuenta: String,
    val correo: String,
    val imagen: String
)