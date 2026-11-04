package com.example.comunamadsistemacitas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.comunamadsistemacitas.view.ListarCitasActivity
import com.example.comunamadsistemacitas.view.RegistrarCitaActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Vincular los botones de la pantalla principal
        val btnRegistrarCita = findViewById<Button>(R.id.btnRegistrarCita)
        val btnVerCitas = findViewById<Button>(R.id.btnVerCitas)

        // Navegación mediante Intents a la Pantalla de Registro
        btnRegistrarCita.setOnClickListener {
            val intent = Intent(this, RegistrarCitaActivity::class.java)
            startActivity(intent)
        }

        // Navegación mediante Intents a la Pantalla de Listado
        btnVerCitas.setOnClickListener {
            val intent = Intent(this, ListarCitasActivity::class.java)
            startActivity(intent)
        }
    }
}
