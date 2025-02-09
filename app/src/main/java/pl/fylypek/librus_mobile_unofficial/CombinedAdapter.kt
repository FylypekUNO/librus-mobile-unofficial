package pl.fylypek.librus_mobile_unofficial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CombinedAdapter(private val items: List<ListItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_SUBJECT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (val item = items[position]) {
            is ListItem.SemesterHeader -> VIEW_TYPE_HEADER
            is ListItem.SubjectItem -> VIEW_TYPE_SUBJECT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_semester_header, parent, false)
                HeaderViewHolder(view)
            }
            VIEW_TYPE_SUBJECT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_subject, parent, false)
                SubjectViewHolder(view)
            }
            else -> throw IllegalArgumentException("Nieznany typ widoku")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ListItem.SemesterHeader -> (holder as HeaderViewHolder).bind(item)
            is ListItem.SubjectItem -> (holder as SubjectViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvSemesterTitle: TextView = itemView.findViewById(R.id.tvSemesterTitle)
        fun bind(item: ListItem.SemesterHeader) {
            tvSemesterTitle.text = item.title
        }
    }

    class SubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvSubjectName: TextView = itemView.findViewById(R.id.tvSubjectName)
        private val tvSubjectGrades: TextView = itemView.findViewById(R.id.tvSubjectGrades)
        fun bind(item: ListItem.SubjectItem) {
            tvSubjectName.text = item.subject.name
            tvSubjectGrades.text = item.subject.grades.joinToString(separator = ", ")
        }
    }
}
