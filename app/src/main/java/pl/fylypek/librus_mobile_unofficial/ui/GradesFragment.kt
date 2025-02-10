package pl.fylypek.librus_mobile_unofficial.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import pl.fylypek.librus_mobile_unofficial.R
import pl.fylypek.librus_mobile_unofficial.gradesData

class GradesFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var rvGrades: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_grades, container, false)
        tabLayout = view.findViewById(R.id.tabLayoutGrades)
        rvGrades = view.findViewById(R.id.rvGrades)
        rvGrades.layoutManager = LinearLayoutManager(context)

        gradesData.forEach { semester ->
            tabLayout.addTab(tabLayout.newTab().setText(semester.name))
        }

        if (gradesData.isNotEmpty()) {
            rvGrades.adapter = SubjectAdapter(gradesData[0].subjects)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position ?: 0
                if (position in gradesData.indices) {
                    val selectedSemester = gradesData[position]
                    rvGrades.adapter = SubjectAdapter(selectedSemester.subjects)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        return view
    }
}
