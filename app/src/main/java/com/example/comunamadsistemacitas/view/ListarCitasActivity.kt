package com.example.comunamadsistemacitas.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.comunamadsistemacitas.R
import com.example.comunamadsistemacitas.data.repository.CitaRepository
import com.example.comunamadsistemacitas.view.adapter.CitaAdapter

class ListarCitasActivity : AppCompatActivity() {

    private lateinit var repository: CitaRepository
    private lateinit var adapter: CitaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_citas)

        repository = CitaRepository(this)
        val rvCitas = findViewById<RecyclerView>(R.id.rvCitas)
        rvCitas.layoutManager = LinearLayoutManager(this)

        // Configurar el adaptador para enviar los datos de la cita seleccionada a DetalleCitaActivity
        adapter = CitaAdapter(emptyList()) { citaSeleccionada ->
            val intent = Intent(this, DetalleCitaActivity::class.java).apply {
                putExtra("CITA_ID", citaSeleccionada.id)
                putExtra("CITA_CLIENTE", citaSeleccionada.cliente)
                putExtra("CITA_SERVICIO", citaSeleccionada.servicio)
                putExtra("CITA_PRECIO", citaSeleccionada.precio)
                putExtra("CITA_FECHA", citaSeleccionada.fechaHora)
                putExtra("CITA_ESTADO", citaSeleccionada.estado.name)
            }
            startActivity(intent)
        }

        rvCitas.adapter = adapter
    }

    // Refresca la lista desde SQLite al volver a esta pantalla
    override fun onResume() {
        super.onResume()
        val lista = repository.obtenerTodasLasCitas()
        adapter.actualizarLista(lista)
    }
}