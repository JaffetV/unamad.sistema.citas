package com.example.comunamadsistemacitas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.comunamadsistemacitas.model.Cita

class CitaAdapter(
    private var listaCitas: List<Cita>,
    private val onItemClick: (Cita) -> Unit
) : RecyclerView.Adapter<CitaAdapter.CitaViewHolder>() {

    class CitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // IDs coincidentes con item_cita.xml
        val tvCliente: TextView = itemView.findViewById(R.id.tvCliente)
        val tvServicio: TextView = itemView.findViewById(R.id.tvServicio)
        val tvFechaHora: TextView = itemView.findViewById(R.id.tvFechaHora)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
        val tvEstado: TextView = itemView.findViewById(R.id.tvEstado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = listaCitas[position]

        holder.tvCliente.text = cita.cliente
        holder.tvServicio.text = cita.servicio
        holder.tvFechaHora.text = cita.fechaHora
        holder.tvPrecio.text = "S/ ${cita.precio}"

        // Se agrega .name para convertir el Enum EstadoCita a String
        holder.tvEstado.text = cita.estado.name

        holder.itemView.setOnClickListener {
            onItemClick(cita)
        }
    }

    override fun getItemCount(): Int = listaCitas.size

    fun actualizarLista(nuevaLista: List<Cita>) {
        listaCitas = nuevaLista
        notifyDataSetChanged()
    }
}