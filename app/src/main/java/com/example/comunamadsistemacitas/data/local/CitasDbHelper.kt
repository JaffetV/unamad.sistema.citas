package com.example.comunamadsistemacitas.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * CitasDbHelper: Base de Datos Relacional Segura con Claves Foráneas (Foreign Keys)
 * y soporte para Clientes, Servicios (Soft Delete) y Citas normalizadas.
 */
class CitasDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "citas_unamad.db"
        private const val DATABASE_VERSION = 6

        // Tabla Clientes
        const val TABLE_CLIENTES = "clientes"
        const val COLUMN_CLIENTE_ID = "id"
        const val COLUMN_CLIENTE_NOMBRE = "nombre"
        const val COLUMN_CLIENTE_TELEFONO = "telefono"
        const val COLUMN_CLIENTE_FECHA_REGISTRO = "fecha_registro"

        // Tabla Servicios
        const val TABLE_SERVICIOS = "servicios"
        const val COLUMN_SERVICIO_ID = "id"
        const val COLUMN_SERVICIO_NOMBRE = "nombre"
        const val COLUMN_SERVICIO_EMOJI = "emoji"
        const val COLUMN_SERVICIO_PRECIO_BASE = "precio_base"
        const val COLUMN_SERVICIO_DURACION = "duracion_minutos"
        const val COLUMN_SERVICIO_ACTIVO = "activo"

        // Tabla Citas
        const val TABLE_CITAS = "citas"
        const val COLUMN_ID = "id"
        const val COLUMN_FK_CLIENTE_ID = "cliente_id"
        const val COLUMN_FK_SERVICIO_ID = "servicio_id"
        const val COLUMN_CLIENTE = "cliente"
        const val COLUMN_TELEFONO = "telefono"
        const val COLUMN_SERVICIO = "servicio"
        const val COLUMN_PRECIO = "precio_cobrado"
        const val COLUMN_MONTO_SENA = "monto_sena"
        const val COLUMN_FECHA_HORA = "fecha_inicio"
        const val COLUMN_FECHA_FIN = "fecha_fin"
        const val COLUMN_ESTADO = "estado"
        const val COLUMN_METODO_PAGO = "metodo_pago"
        const val COLUMN_DURACION_MINUTOS = "duracion_minutos"
        const val COLUMN_NOTA = "nota"
    }

    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
        db?.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // 1. Crear Tabla Clientes
        val createClientes = """
            CREATE TABLE $TABLE_CLIENTES (
                $COLUMN_CLIENTE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CLIENTE_NOMBRE TEXT NOT NULL,
                $COLUMN_CLIENTE_TELEFONO TEXT NOT NULL,
                $COLUMN_CLIENTE_FECHA_REGISTRO TEXT NOT NULL
            )
        """.trimIndent()
        db?.execSQL(createClientes)

        // 2. Crear Tabla Servicios
        val createServicios = """
            CREATE TABLE $TABLE_SERVICIOS (
                $COLUMN_SERVICIO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_SERVICIO_NOMBRE TEXT NOT NULL,
                $COLUMN_SERVICIO_EMOJI TEXT NOT NULL,
                $COLUMN_SERVICIO_PRECIO_BASE REAL NOT NULL,
                $COLUMN_SERVICIO_DURACION INTEGER NOT NULL,
                $COLUMN_SERVICIO_ACTIVO INTEGER DEFAULT 1
            )
        """.trimIndent()
        db?.execSQL(createServicios)

        // 3. Crear Tabla Citas con Claves Foráneas (FK)
        val createCitas = """
            CREATE TABLE $TABLE_CITAS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_FK_CLIENTE_ID INTEGER,
                $COLUMN_FK_SERVICIO_ID INTEGER,
                $COLUMN_CLIENTE TEXT NOT NULL,
                $COLUMN_TELEFONO TEXT,
                $COLUMN_SERVICIO TEXT NOT NULL,
                $COLUMN_PRECIO REAL NOT NULL,
                $COLUMN_MONTO_SENA REAL DEFAULT 0.0,
                $COLUMN_FECHA_HORA TEXT NOT NULL,
                $COLUMN_FECHA_FIN TEXT,
                $COLUMN_ESTADO TEXT NOT NULL,
                $COLUMN_METODO_PAGO TEXT DEFAULT 'Efectivo',
                $COLUMN_DURACION_MINUTOS INTEGER DEFAULT 30,
                $COLUMN_NOTA TEXT,
                FOREIGN KEY ($COLUMN_FK_CLIENTE_ID) REFERENCES $TABLE_CLIENTES($COLUMN_CLIENTE_ID) ON DELETE SET NULL,
                FOREIGN KEY ($COLUMN_FK_SERVICIO_ID) REFERENCES $TABLE_SERVICIOS($COLUMN_SERVICIO_ID) ON DELETE SET NULL
            )
        """.trimIndent()
        db?.execSQL(createCitas)

        // Poblar catálogo de servicios inicial
        poblarServiciosIniciales(db)
    }

    private fun poblarServiciosIniciales(db: SQLiteDatabase?) {
        val serviciosIniciales = arrayOf(
            "INSERT INTO $TABLE_SERVICIOS ($COLUMN_SERVICIO_NOMBRE, $COLUMN_SERVICIO_EMOJI, $COLUMN_SERVICIO_PRECIO_BASE, $COLUMN_SERVICIO_DURACION, $COLUMN_SERVICIO_ACTIVO) VALUES ('Corte de Cabello', '✂️', 25.0, 30, 1)",
            "INSERT INTO $TABLE_SERVICIOS ($COLUMN_SERVICIO_NOMBRE, $COLUMN_SERVICIO_EMOJI, $COLUMN_SERVICIO_PRECIO_BASE, $COLUMN_SERVICIO_DURACION, $COLUMN_SERVICIO_ACTIVO) VALUES ('Barbería / Barba', '🧔', 20.0, 20, 1)",
            "INSERT INTO $TABLE_SERVICIOS ($COLUMN_SERVICIO_NOMBRE, $COLUMN_SERVICIO_EMOJI, $COLUMN_SERVICIO_PRECIO_BASE, $COLUMN_SERVICIO_DURACION, $COLUMN_SERVICIO_ACTIVO) VALUES ('Manicura / Pedicura', '💅', 35.0, 45, 1)",
            "INSERT INTO $TABLE_SERVICIOS ($COLUMN_SERVICIO_NOMBRE, $COLUMN_SERVICIO_EMOJI, $COLUMN_SERVICIO_PRECIO_BASE, $COLUMN_SERVICIO_DURACION, $COLUMN_SERVICIO_ACTIVO) VALUES ('Tinte / Colorimetría', '🎨', 90.0, 90, 1)",
            "INSERT INTO $TABLE_SERVICIOS ($COLUMN_SERVICIO_NOMBRE, $COLUMN_SERVICIO_EMOJI, $COLUMN_SERVICIO_PRECIO_BASE, $COLUMN_SERVICIO_DURACION, $COLUMN_SERVICIO_ACTIVO) VALUES ('Tratamiento / Masaje', '💆', 70.0, 60, 1)",
            "INSERT INTO $TABLE_SERVICIOS ($COLUMN_SERVICIO_NOMBRE, $COLUMN_SERVICIO_EMOJI, $COLUMN_SERVICIO_PRECIO_BASE, $COLUMN_SERVICIO_DURACION, $COLUMN_SERVICIO_ACTIVO) VALUES ('Consulta Médica General', '🩺', 50.0, 30, 1)",
            "INSERT INTO $TABLE_SERVICIOS ($COLUMN_SERVICIO_NOMBRE, $COLUMN_SERVICIO_EMOJI, $COLUMN_SERVICIO_PRECIO_BASE, $COLUMN_SERVICIO_DURACION, $COLUMN_SERVICIO_ACTIVO) VALUES ('Atención Odontológica', '🦷', 80.0, 45, 1)",
            "INSERT INTO $TABLE_SERVICIOS ($COLUMN_SERVICIO_NOMBRE, $COLUMN_SERVICIO_EMOJI, $COLUMN_SERVICIO_PRECIO_BASE, $COLUMN_SERVICIO_DURACION, $COLUMN_SERVICIO_ACTIVO) VALUES ('Mantenimiento General', '🛠️', 60.0, 60, 1)"
        )
        for (query in serviciosIniciales) {
            db?.execSQL(query)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CITAS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_SERVICIOS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CLIENTES")
        onCreate(db)
    }
}
