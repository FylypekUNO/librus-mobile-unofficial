package pl.fylypek.librus_mobile_unofficial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SemesterHeaderAdapter(private val title: String) :
    RecyclerView.Adapter<SemesterHeaderAdapter.HeaderViewHolder>() {

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSemesterTitle: TextView = itemView.findViewById(R.id.tvSemesterTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_semester_header, parent, false)
        return HeaderViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.tvSemesterTitle.text = title
    }

    override fun getItemCount(): Int = 1
}
