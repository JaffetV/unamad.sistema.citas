package com.example.comunamadsistemacitas

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListarCitasActivity : AppCompatActivity() {

    private lateinit var rvCitas: RecyclerView
    // private lateinit var adapter: CitaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_citas)

        // Vinculación del RecyclerView con el ID de activity_listar_citas.xml
        rvCitas = findViewById(R.id.rvCitas)
        rvCitas.layoutManager = LinearLayoutManager(this)

        // Asignación de adaptador (descomentar al vincular con tu repositorio)
        /*
        val listaCitas = repository.obtenerCitas()
        adapter = CitaAdapter(listaCitas) { cita ->
            val intent = Intent(this, DetalleCitaActivity::class.java)
            intent.putExtra("CITA_ID", cita.id)
            startActivity(intent)
        }
        rvCitas.adapter = adapter
        */
    }

    override fun onResume() {
        super.onResume()
        // Si tu repositorio actualiza datos al regresar a la lista:
        // adapter.notificarCambios(repository.obtenerCitas())
    }
}