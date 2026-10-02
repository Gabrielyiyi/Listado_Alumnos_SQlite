package com.example.listado_alumnos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listado_alumnos.databinding.ActivityMainBinding
import com.example.listado_alumnos.databinding.DialogAlumnoBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: DBHelperAlumno
    private lateinit var alumnoAdapter: AlumnoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = DBHelperAlumno(this)

        setupRecyclerView()
        setupListeners()
        cargarAlumnos()
    }

    private fun setupRecyclerView() {
        alumnoAdapter = AlumnoAdapter(
            listaAlumnos = emptyList(),
            onEditClick = { alumno -> mostrarDialogoEditarAlumno(alumno) },
            onDeleteClick = { alumno -> mostrarDialogoEliminarAlumno(alumno) }
        )

        binding.rvAlumnos.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = alumnoAdapter
        }
    }

    private fun setupListeners() {
        binding.fabAgregar.setOnClickListener {
            mostrarDialogoAgregarAlumno()
        }

        binding.btnEmptyAgregar.setOnClickListener {
            mostrarDialogoAgregarAlumno()
        }
    }

    private fun cargarAlumnos() {
        val lista = dbHelper.obtenerAlumnos()
        alumnoAdapter.actualizarLista(lista)

        binding.tvSubtitle.text = getString(R.string.subtitle_alumnos_count, lista.size)

        if (lista.isEmpty()) {
            binding.layoutEmptyState.visibility = View.VISIBLE
            binding.rvAlumnos.visibility = View.GONE
        } else {
            binding.layoutEmptyState.visibility = View.GONE
            binding.rvAlumnos.visibility = View.VISIBLE
        }
    }

    private fun mostrarDialogoAgregarAlumno() {
        val dialogBinding = DialogAlumnoBinding.inflate(LayoutInflater.from(this))

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.agregar_alumno))
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.guardar)) { dialog, _ ->
                val nombre = dialogBinding.etNombre.text.toString().trim()
                val cuenta = dialogBinding.etCuenta.text.toString().trim()
                val correo = dialogBinding.etCorreo.text.toString().trim()
                val imagen = dialogBinding.etImagen.text.toString().trim()

                if (nombre.isNotEmpty() && cuenta.isNotEmpty()) {
                    val nuevoAlumno = Alumno(
                        nombre = nombre,
                        cuenta = cuenta,
                        correo = correo,
                        imagen = imagen
                    )
                    val result = dbHelper.insertarAlumno(nuevoAlumno)
                    if (result != -1L) {
                        Toast.makeText(this, "Alumno agregado correctamente", Toast.LENGTH_SHORT).show()
                        cargarAlumnos()
                    } else {
                        Toast.makeText(this, "Error al guardar el alumno", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Por favor llena los campos requeridos", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancelar)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun mostrarDialogoEditarAlumno(alumno: Alumno) {
        val dialogBinding = DialogAlumnoBinding.inflate(LayoutInflater.from(this))

        dialogBinding.etNombre.setText(alumno.nombre)
        dialogBinding.etCuenta.setText(alumno.cuenta)
        dialogBinding.etCorreo.setText(alumno.correo)
        dialogBinding.etImagen.setText(alumno.imagen)

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.editar_alumno))
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.guardar)) { dialog, _ ->
                val nombre = dialogBinding.etNombre.text.toString().trim()
                val cuenta = dialogBinding.etCuenta.text.toString().trim()
                val correo = dialogBinding.etCorreo.text.toString().trim()
                val imagen = dialogBinding.etImagen.text.toString().trim()

                if (nombre.isNotEmpty() && cuenta.isNotEmpty()) {
                    val alumnoActualizado = Alumno(
                        id = alumno.id,
                        nombre = nombre,
                        cuenta = cuenta,
                        correo = correo,
                        imagen = imagen
                    )
                    val rowsAffected = dbHelper.actualizarAlumno(alumnoActualizado)
                    if (rowsAffected > 0) {
                        Toast.makeText(this, "Alumno actualizado correctamente", Toast.LENGTH_SHORT).show()
                        cargarAlumnos()
                    } else {
                        Toast.makeText(this, "Error al actualizar el alumno", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Por favor llena los campos requeridos", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancelar)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun mostrarDialogoEliminarAlumno(alumno: Alumno) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.eliminar_alumno))
            .setMessage(getString(R.string.confirmar_eliminar, alumno.nombre))
            .setPositiveButton(getString(R.string.eliminar)) { dialog, _ ->
                val rowsAffected = dbHelper.eliminarAlumno(alumno.id)
                if (rowsAffected > 0) {
                    Toast.makeText(this, "Alumno eliminado correctamente", Toast.LENGTH_SHORT).show()
                    cargarAlumnos()
                } else {
                    Toast.makeText(this, "Error al eliminar el alumno", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancelar)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}