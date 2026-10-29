package com.example.comunamadsistemacitas.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.comunamadsistemacitas.R
import com.example.comunamadsistemacitas.model.Cita

class CitaAdapter(
    private var listaCitas: List<Cita>,
    private val onItemClick: (Cita) -> Unit
) : RecyclerView.Adapter<CitaAdapter.CitaViewHolder>() {

    class CitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCliente: TextView = itemView.findViewById(R.id.tvItemCliente)
        val tvServicio: TextView = itemView.findViewById(R.id.tvItemServicio)
        val tvFechaHora: TextView = itemView.findViewById(R.id.tvItemFechaHora)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvItemPrecio)
        val tvEstado: TextView = itemView.findViewById(R.id.tvItemEstado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = listaCitas[position]

        holder.tvCliente.text = "Cliente: ${cita.cliente}"
        holder.tvServicio.text = "Servicio: ${cita.servicio}"
        holder.tvFechaHora.text = "Fecha/Hora: ${cita.fechaHora}"
        holder.tvPrecio.text = "S/ ${cita.precio}"
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