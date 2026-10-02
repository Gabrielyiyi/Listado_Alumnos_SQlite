package com.example.listado_alumnos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelperAlumno(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Alumnos.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_ALUMNOS = "Alumnos"
        const val COLUMN_ID = "id"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_CUENTA = "cuenta"
        const val COLUMN_CORREO = "correo"
        const val COLUMN_IMAGEN = "imagen"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableStatement = """
            CREATE TABLE $TABLE_ALUMNOS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOMBRE TEXT NOT NULL,
                $COLUMN_CUENTA TEXT NOT NULL,
                $COLUMN_CORREO TEXT NOT NULL,
                $COLUMN_IMAGEN TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableStatement)

        // Insert initial sample data
        insertarDatosIniciales(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ALUMNOS")
        onCreate(db)
    }

    private fun insertarDatosIniciales(db: SQLiteDatabase) {
        val alumnosIniciales = listOf(
            Alumno(
                nombre = "Carlos González",
                cuenta = "20210001",
                correo = "carlos.gonzalez@example.com",
                imagen = "https://reqres.in/img/faces/1-image.jpg"
            ),
            Alumno(
                nombre = "María Rodríguez",
                cuenta = "20210002",
                correo = "maria.rodriguez@example.com",
                imagen = "https://reqres.in/img/faces/2-image.jpg"
            ),
            Alumno(
                nombre = "Juan Martínez",
                cuenta = "20210003",
                correo = "juan.martinez@example.com",
                imagen = "https://reqres.in/img/faces/3-image.jpg"
            )
        )

        for (alumno in alumnosIniciales) {
            val values = ContentValues().apply {
                put(COLUMN_NOMBRE, alumno.nombre)
                put(COLUMN_CUENTA, alumno.cuenta)
                put(COLUMN_CORREO, alumno.correo)
                put(COLUMN_IMAGEN, alumno.imagen)
            }
            db.insert(TABLE_ALUMNOS, null, values)
        }
    }

    fun insertarAlumno(alumno: Alumno): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, alumno.nombre)
            put(COLUMN_CUENTA, alumno.cuenta)
            put(COLUMN_CORREO, alumno.correo)
            put(COLUMN_IMAGEN, alumno.imagen)
        }
        val result = db.insert(TABLE_ALUMNOS, null, values)
        db.close()
        return result
    }

    fun obtenerAlumnos(): List<Alumno> {
        val listaAlumnos = mutableListOf<Alumno>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_ALUMNOS ORDER BY $COLUMN_ID DESC", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
                val cuenta = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CUENTA))
                val correo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CORREO))
                val imagen = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN))

                listaAlumnos.add(Alumno(id, nombre, cuenta, correo, imagen))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listaAlumnos
    }

    fun actualizarAlumno(alumno: Alumno): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, alumno.nombre)
            put(COLUMN_CUENTA, alumno.cuenta)
            put(COLUMN_CORREO, alumno.correo)
            put(COLUMN_IMAGEN, alumno.imagen)
        }
        val rowsAffected = db.update(
            TABLE_ALUMNOS,
            values,
            "$COLUMN_ID = ?",
            arrayOf(alumno.id.toString())
        )
        db.close()
        return rowsAffected
    }

    fun eliminarAlumno(id: Int): Int {
        val db = writableDatabase
        val rowsAffected = db.delete(
            TABLE_ALUMNOS,
            "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )
        db.close()
        return rowsAffected
    }
}