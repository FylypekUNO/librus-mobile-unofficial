package pl.fylypek.librus_mobile_unofficial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SubjectAdapter(private val subjects: List<Subject>) :
    RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    inner class SubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSubjectName: TextView = itemView.findViewById(R.id.tvSubjectName)
        val tvSubjectGrades: TextView = itemView.findViewById(R.id.tvSubjectGrades)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subject, parent, false)
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val subject = subjects[position]
        holder.tvSubjectName.text = subject.name
        holder.tvSubjectGrades.text = subject.gradesAsString
    }

    override fun getItemCount(): Int = subjects.size
}
