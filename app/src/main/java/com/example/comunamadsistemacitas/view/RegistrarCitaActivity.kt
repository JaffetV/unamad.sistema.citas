package com.example.comunamadsistemacitas.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.comunamadsistemacitas.R
import com.example.comunamadsistemacitas.data.repository.CitaRepository
import com.example.comunamadsistemacitas.model.Cita
import com.example.comunamadsistemacitas.model.EstadoCita

class RegistrarCitaActivity : AppCompatActivity() {

    private lateinit var repository: CitaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_cita)

        repository = CitaRepository(this)

        val etCliente = findViewById<EditText>(R.id.etCliente)
        val etServicio = findViewById<EditText>(R.id.etServicio)
        val etPrecio = findViewById<EditText>(R.id.etPrecio)
        val etFechaHora = findViewById<EditText>(R.id.etFechaHora)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarCita)

        btnGuardar.setOnClickListener {
            val cliente = etCliente.text.toString().trim()
            val servicio = etServicio.text.toString().trim()
            val precioTexto = etPrecio.text.toString().trim()
            val fechaHora = etFechaHora.text.toString().trim()

            // VALIDACIÓN DE CAMPOS (Requisito de Formulario)
            if (cliente.isEmpty() || servicio.isEmpty() || precioTexto.isEmpty() || fechaHora.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val precio = precioTexto.toDoubleOrNull() ?: 0.0

            // Creación del objeto inmutable Cita
            val nuevaCita = Cita(
                cliente = cliente,
                servicio = servicio,
                precio = precio,
                fechaHora = fechaHora,
                estado = EstadoCita.PENDIENTE
            )

            // Guardar en Base de Datos (CREATE)
            val resultado = repository.insertarCita(nuevaCita)

            if (resultado != -1L) {
                Toast.makeText(this, "Reserva registrada correctamente", Toast.LENGTH_SHORT).show()
                finish() // Cierra la pantalla y regresa al menú
            } else {
                Toast.makeText(this, "Error al guardar en la base de datos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}