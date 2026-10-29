package com.example.comunamadsistemacitas.view

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.comunamadsistemacitas.R
import com.example.comunamadsistemacitas.data.repository.CitaRepository
import com.example.comunamadsistemacitas.model.EstadoCita

class DetalleCitaActivity : AppCompatActivity() {

    private lateinit var repository: CitaRepository
    private var citaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_cita)

        repository = CitaRepository(this)

        val tvCliente = findViewById<TextView>(R.id.tvDetalleCliente)
        val tvServicio = findViewById<TextView>(R.id.tvDetalleServicio)
        val tvPrecio = findViewById<TextView>(R.id.tvDetallePrecio)
        val tvFecha = findViewById<TextView>(R.id.tvDetalleFecha)
        val tvEstado = findViewById<TextView>(R.id.tvDetalleEstado)

        val btnAtendido = findViewById<Button>(R.id.btnMarcarAtendido)
        val btnCancelado = findViewById<Button>(R.id.btnMarcarCancelado)
        val btnEliminar = findViewById<Button>(R.id.btnEliminarCita)

        // Obtener datos enviados desde el Intent
        citaId = intent.getIntExtra("CITA_ID", -1)
        val cliente = intent.getStringExtra("CITA_CLIENTE") ?: ""
        val servicio = intent.getStringExtra("CITA_SERVICIO") ?: ""
        val precio = intent.getDoubleExtra("CITA_PRECIO", 0.0)
        val fecha = intent.getStringExtra("CITA_FECHA") ?: ""
        val estado = intent.getStringExtra("CITA_ESTADO") ?: ""

        // Mostrar los datos en las etiquetas
        tvCliente.text = "Cliente: $cliente"
        tvServicio.text = "Servicio: $servicio"
        tvPrecio.text = "Precio: S/ $precio"
        tvFecha.text = "Fecha/Hora: $fecha"
        tvEstado.text = "Estado: $estado"

        // Evento UPDATE: Cambiar a ATENDIDO
        btnAtendido.setOnClickListener {
            if (citaId != -1) {
                repository.actualizarEstadoCita(citaId, EstadoCita.ATENDIDO)
                Toast.makeText(this, "Estado cambiado a ATENDIDO", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        // Evento UPDATE: Cambiar a CANCELADO
        btnCancelado.setOnClickListener {
            if (citaId != -1) {
                repository.actualizarEstadoCita(citaId, EstadoCita.CANCELADO)
                Toast.makeText(this, "Estado cambiado a CANCELADO", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        // Evento DELETE: Eliminar de la base de datos
        btnEliminar.setOnClickListener {
            if (citaId != -1) {
                repository.eliminarCita(citaId)
                Toast.makeText(this, "Cita eliminada correctamente", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}