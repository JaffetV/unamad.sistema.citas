package com.example.comunamadsistemacitas.view

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.comunamadsistemacitas.R
import com.example.comunamadsistemacitas.data.repository.CitaRepository
import com.example.comunamadsistemacitas.model.EstadoCita
import com.google.android.material.button.MaterialButton
import java.util.Locale

/**
 * DetalleCitaActivity: Muestra los detalles completos de una reserva.
 * Permite cambiar el estado de la cita (ATENDIDO / CANCELADO) o eliminarla con confirmación previa.
 */
class DetalleCitaActivity : AppCompatActivity() {

    private lateinit var repository: CitaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_cita)

        repository = CitaRepository(this)

        val tvDetalleCita = findViewById<TextView>(R.id.tvDetalleCita)
        val btnAtendido = findViewById<MaterialButton>(R.id.btnAtendido)
        val btnCancelado = findViewById<MaterialButton>(R.id.btnCancelado)
        val btnEliminar = findViewById<MaterialButton>(R.id.btnEliminar)

        val citaId = intent.getIntExtra("CITA_ID", -1)

        if (citaId != -1) {
            val cita = repository.obtenerCitaPorId(citaId)
            if (cita != null) {
                tvDetalleCita?.text = """
                    Cliente: ${cita.cliente}
                    Servicio: ${cita.servicio}
                    Precio: S/ ${String.format(Locale.getDefault(), "%.2f", cita.precio)}
                    Fecha y Hora: ${cita.fechaHora}
                    Estado Actual: ${cita.estado.name}
                """.trimIndent()
            } else {
                tvDetalleCita?.text = "No se encontraron los datos de la cita."
            }
        } else {
            tvDetalleCita?.text = "ID de cita no válido."
        }

        // Acción: Marcar como Atendido (UPDATE)
        btnAtendido?.setOnClickListener {
            if (citaId != -1) {
                repository.actualizarEstadoCita(citaId, EstadoCita.ATENDIDO)
                Toast.makeText(this, "Estado cambiado a ATENDIDO", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        // Acción: Cancelar Cita (UPDATE)
        btnCancelado?.setOnClickListener {
            if (citaId != -1) {
                repository.actualizarEstadoCita(citaId, EstadoCita.CANCELADO)
                Toast.makeText(this, "Estado cambiado a CANCELADO", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        // Acción: Eliminar Cita con Diálogo de Confirmación (DELETE)
        btnEliminar?.setOnClickListener {
            if (citaId != -1) {
                AlertDialog.Builder(this)
                    .setTitle("Confirmar eliminación")
                    .setMessage("¿Estás seguro de que deseas eliminar permanentemente esta cita?")
                    .setPositiveButton("Eliminar") { _, _ ->
                        repository.eliminarCita(citaId)
                        Toast.makeText(this, "Cita eliminada permanentemente", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            }
        }
    }
}
