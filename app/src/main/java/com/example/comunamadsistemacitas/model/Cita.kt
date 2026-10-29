package com.example.comunamadsistemacitas.model

data class Cita(
    val id: Int = 0,
    val cliente: String,
    val servicio: String,
    val precio: Double,
    val fechaHora: String,
    val estado: EstadoCita = EstadoCita.PENDIENTE
)