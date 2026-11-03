package com.example.comunamadsistemacitas

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class DetalleCitaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_cita)

        // Vinculación de elementos visuales
        val tvDetalleCita = findViewById<TextView>(R.id.tvDetalleCita)
        val btnAtendido = findViewById<MaterialButton>(R.id.btnAtendido)
        val btnCancelado = findViewById<MaterialButton>(R.id.btnCancelado)
        val btnEliminar = findViewById<MaterialButton>(R.id.btnEliminar)

        val citaId = intent.getIntExtra("CITA_ID", -1)

        // Acción: Marcar como Atendido
        btnAtendido?.setOnClickListener {
            if (citaId != -1) {
                // repository.actualizarEstadoCita(citaId, "ATENDIDO")
                Toast.makeText(this, "Estado cambiado a ATENDIDO", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        // Acción: Cancelar Cita
        btnCancelado?.setOnClickListener {
            if (citaId != -1) {
                // repository.actualizarEstadoCita(citaId, "CANCELADO")
                Toast.makeText(this, "Estado cambiado a CANCELADO", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        // Acción: Eliminar Cita
        btnEliminar?.setOnClickListener {
            if (citaId != -1) {
                // repository.eliminarCita(citaId)
                Toast.makeText(this, "Cita eliminada correctamente", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}