package com.example.comunamadsistemacitas.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.comunamadsistemacitas.R
import com.example.comunamadsistemacitas.data.repository.CitaRepository
import com.example.comunamadsistemacitas.model.Cita
import com.example.comunamadsistemacitas.model.EstadoCita
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar
import java.util.Locale

/**
 * RegistrarCitaActivity: Pantalla para el registro de reservas/citas.
 * Incluye selección interactiva de fecha/hora mediante Pickers nativos y menú desplegable de servicios.
 */
class RegistrarCitaActivity : AppCompatActivity() {

    private lateinit var repository: CitaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_cita)

        // Inicialización del repositorio de datos local (SQLite)
        repository = CitaRepository(this)

        val tilCliente = findViewById<TextInputLayout>(R.id.tilCliente)
        val tilServicio = findViewById<TextInputLayout>(R.id.tilServicio)
        val tilPrecio = findViewById<TextInputLayout>(R.id.tilPrecio)
        val tilFechaHora = findViewById<TextInputLayout>(R.id.tilFechaHora)

        val etCliente = findViewById<TextInputEditText>(R.id.etCliente)
        val etServicio = findViewById<AutoCompleteTextView>(R.id.etServicio)
        val etPrecio = findViewById<TextInputEditText>(R.id.etPrecio)
        val etFechaHora = findViewById<TextInputEditText>(R.id.etFechaHora)
        val btnGuardar = findViewById<MaterialButton>(R.id.btnGuardar)

        // Configuración del menú desplegable de servicios
        val servicios = arrayOf(
            "Consulta Médica General",
            "Atención Odontológica",
            "Servicio de Psicología",
            "Asesoría Técnica / Legal",
            "Corte de Cabello / Estética",
            "Mantenimiento General"
        )
        val adapterServicios = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, servicios)
        etServicio?.setAdapter(adapterServicios)

        // Desactivar teclado directo en fecha y hora para forzar el uso de DatePicker/TimePicker
        etFechaHora?.keyListener = null
        etFechaHora?.isFocusable = false
        etFechaHora?.isClickable = true
        etFechaHora?.setOnClickListener {
            mostrarDatePickerDialog { fechaSeleccionada ->
                mostrarTimePickerDialog { horaSeleccionada ->
                    etFechaHora.setText(String.format(Locale.getDefault(), "%s %s", fechaSeleccionada, horaSeleccionada))
                }
            }
        }

        // Evento Guardar Reserva con validación interactiva de formulario
        btnGuardar?.setOnClickListener {
            val cliente = etCliente?.text.toString().trim()
            val servicio = etServicio?.text.toString().trim()
            val precioStr = etPrecio?.text.toString().trim().replace(',', '.')
            val fechaHora = etFechaHora?.text.toString().trim()

            var esValido = true

            if (cliente.isEmpty()) {
                tilCliente?.error = "Ingrese el nombre del cliente"
                esValido = false
            } else {
                tilCliente?.error = null
            }

            if (servicio.isEmpty()) {
                tilServicio?.error = "Seleccione un servicio"
                esValido = false
            } else {
                tilServicio?.error = null
            }

            if (precioStr.isEmpty()) {
                tilPrecio?.error = "Ingrese el precio del servicio"
                esValido = false
            } else {
                tilPrecio?.error = null
            }

            if (fechaHora.isEmpty()) {
                tilFechaHora?.error = "Seleccione fecha y hora"
                esValido = false
            } else {
                tilFechaHora?.error = null
            }

            if (esValido) {
                try {
                    val precio = precioStr.toDoubleOrNull() ?: 0.0
                    val nuevaCita = Cita(
                        cliente = cliente,
                        servicio = servicio,
                        precio = precio,
                        fechaHora = fechaHora,
                        estado = EstadoCita.PENDIENTE
                    )
                    val id = repository.insertarCita(nuevaCita)
                    if (id != -1L) {
                        Toast.makeText(this, "Reserva registrada con éxito", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Error al guardar en la base de datos", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al guardar: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun mostrarDatePickerDialog(onFechaSeleccionada: (String) -> Unit) {
        val calendario = Calendar.getInstance()
        val anio = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, year, month, dayOfMonth ->
            val fechaFormateada = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year)
            onFechaSeleccionada(fechaFormateada)
        }, anio, mes, dia).show()
    }

    private fun mostrarTimePickerDialog(onHoraSeleccionada: (String) -> Unit) {
        val calendario = Calendar.getInstance()
        val hora = calendario.get(Calendar.HOUR_OF_DAY)
        val minuto = calendario.get(Calendar.MINUTE)

        TimePickerDialog(this, { _, hourOfDay, minute ->
            val horaFormateada = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
            onHoraSeleccionada(horaFormateada)
        }, hora, minuto, true).show()
    }
}
