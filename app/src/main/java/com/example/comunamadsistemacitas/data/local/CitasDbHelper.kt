package com.example.comunamadsistemacitas.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CitasDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "citas_unamad.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_CITAS = "citas"
        const val COLUMN_ID = "id"
        const val COLUMN_CLIENTE = "cliente"
        const val COLUMN_SERVICIO = "servicio"
        const val COLUMN_PRECIO = "precio"
        const val COLUMN_FECHA_HORA = "fecha_hora"
        const val COLUMN_ESTADO = "estado"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_CITAS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CLIENTE TEXT NOT NULL,
                $COLUMN_SERVICIO TEXT NOT NULL,
                $COLUMN_PRECIO REAL NOT NULL,
                $COLUMN_FECHA_HORA TEXT NOT NULL,
                $COLUMN_ESTADO TEXT NOT NULL
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CITAS")
        onCreate(db)
    }
}