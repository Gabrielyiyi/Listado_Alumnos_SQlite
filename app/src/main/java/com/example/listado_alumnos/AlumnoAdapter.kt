package com.example.listado_alumnos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.listado_alumnos.databinding.ItemPersonaBinding

class AlumnoAdapter(
    private var listaAlumnos: List<Alumno>,
    private val onEditClick: (Alumno) -> Unit,
    private val onDeleteClick: (Alumno) -> Unit
) : RecyclerView.Adapter<AlumnoAdapter.AlumnoViewHolder>() {

    class AlumnoViewHolder(val binding: ItemPersonaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnoViewHolder {
        val binding = ItemPersonaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AlumnoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlumnoViewHolder, position: Int) {
        val alumno = listaAlumnos[position]
        with(holder.binding) {
            tvNombre.text = alumno.nombre
            tvCuenta.text = root.context.getString(R.string.cuenta_format, alumno.cuenta)
            tvCorreo.text = root.context.getString(R.string.correo_format, alumno.correo)

            Glide.with(root.context)
                .load(alumno.imagen)
                .placeholder(R.drawable.ic_person_placeholder)
                .error(R.drawable.ic_person_placeholder)
                .into(imgFoto)

            btnEditar.setOnClickListener {
                onEditClick(alumno)
            }

            btnEliminar.setOnClickListener {
                onDeleteClick(alumno)
            }
        }
    }

    override fun getItemCount(): Int = listaAlumnos.size

    fun actualizarLista(nuevaLista: List<Alumno>) {
        listaAlumnos = nuevaLista
        notifyDataSetChanged()
    }
}