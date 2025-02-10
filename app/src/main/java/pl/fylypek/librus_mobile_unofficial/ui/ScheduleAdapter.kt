package pl.fylypek.librus_mobile_unofficial.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.fylypek.librus_mobile_unofficial.R
import pl.fylypek.librus_mobile_unofficial.data.ScheduleItem

class ScheduleAdapter(private val items: List<ScheduleItem>) :
    RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    inner class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val tvSubject: TextView = itemView.findViewById(R.id.tvSubject)
        val tvTeacher: TextView = itemView.findViewById(R.id.tvTeacher)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_schedule, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val item = items[position]
        holder.tvTime.text = item.time
        holder.tvSubject.text = item.subject
        holder.tvTeacher.text = item.teacher
    }

    override fun getItemCount(): Int = items.size
}
