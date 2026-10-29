package com.example.comunamadsistemacitas.data.repository

import android.content.ContentValues
import android.content.Context
import com.example.comunamadsistemacitas.data.local.CitasDbHelper
import com.example.comunamadsistemacitas.model.Cita
import com.example.comunamadsistemacitas.model.EstadoCita

class CitaRepository(context: Context) {

    private val dbHelper = CitasDbHelper(context)

    // Crear cita (CREATE)
    fun insertarCita(cita: Cita): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(CitasDbHelper.COLUMN_CLIENTE, cita.cliente)
            put(CitasDbHelper.COLUMN_SERVICIO, cita.servicio)
            put(CitasDbHelper.COLUMN_PRECIO, cita.precio)
            put(CitasDbHelper.COLUMN_FECHA_HORA, cita.fechaHora)
            put(CitasDbHelper.COLUMN_ESTADO, cita.estado.name)
        }
        val idInsertado = db.insert(CitasDbHelper.TABLE_CITAS, null, values)
        db.close()
        return idInsertado
    }

    // Obtener todas las citas (READ)
    fun obtenerTodasLasCitas(): List<Cita> {
        val listaCitas = mutableListOf<Cita>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${CitasDbHelper.TABLE_CITAS} ORDER BY ${CitasDbHelper.COLUMN_ID} DESC", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(CitasDbHelper.COLUMN_ID))
                val cliente = cursor.getString(cursor.getColumnIndexOrThrow(CitasDbHelper.COLUMN_CLIENTE))
                val servicio = cursor.getString(cursor.getColumnIndexOrThrow(CitasDbHelper.COLUMN_SERVICIO))
                val precio = cursor.getDouble(cursor.getColumnIndexOrThrow(CitasDbHelper.COLUMN_PRECIO))
                val fechaHora = cursor.getString(cursor.getColumnIndexOrThrow(CitasDbHelper.COLUMN_FECHA_HORA))
                val estadoString = cursor.getString(cursor.getColumnIndexOrThrow(CitasDbHelper.COLUMN_ESTADO))

                val cita = Cita(
                    id = id,
                    cliente = cliente,
                    servicio = servicio,
                    precio = precio,
                    fechaHora = fechaHora,
                    estado = EstadoCita.valueOf(estadoString)
                )
                listaCitas.add(cita)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaCitas
    }

    // Actualizar estado (UPDATE)
    fun actualizarEstadoCita(idCita: Int, nuevoEstado: EstadoCita): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(CitasDbHelper.COLUMN_ESTADO, nuevoEstado.name)
        }
        val filasAfectadas = db.update(
            CitasDbHelper.TABLE_CITAS,
            values,
            "${CitasDbHelper.COLUMN_ID} = ?",
            arrayOf(idCita.toString())
        )
        db.close()
        return filasAfectadas
    }

    // Eliminar cita (DELETE)
    fun eliminarCita(idCita: Int): Int {
        val db = dbHelper.writableDatabase
        val filasEliminadas = db.delete(
            CitasDbHelper.TABLE_CITAS,
            "${CitasDbHelper.COLUMN_ID} = ?",
            arrayOf(idCita.toString())
        )
        db.close()
        return filasEliminadas
    }
}