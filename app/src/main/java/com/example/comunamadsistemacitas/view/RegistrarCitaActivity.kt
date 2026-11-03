package com.example.comunamadsistemacitas

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class RegistrarCitaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_cita)

        // Vinculación de vistas con los nuevos IDs
        val etCliente = findViewById<TextInputEditText>(R.id.etCliente)
        val etServicio = findViewById<TextInputEditText>(R.id.etServicio)
        val etPrecio = findViewById<TextInputEditText>(R.id.etPrecio)
        val etFechaHora = findViewById<TextInputEditText>(R.id.etFechaHora)
        val btnGuardar = findViewById<MaterialButton>(R.id.btnGuardar)

        btnGuardar?.setOnClickListener {
            val cliente = etCliente?.text.toString().trim()
            val servicio = etServicio?.text.toString().trim()
            val precio = etPrecio?.text.toString().trim()
            val fechaHora = etFechaHora?.text.toString().trim()

            if (cliente.isNotEmpty() && servicio.isNotEmpty()) {
                // Lógica de registro en tu base de datos / repositorio
                Toast.makeText(this, "Reserva registrada con éxito", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Por favor completa los campos requeridos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}