package pl.fylypek.librus_mobile_unofficial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import org.threeten.bp.DayOfWeek

class ScheduleActivity : AppCompatActivity() {

    private lateinit var tabDays: TabLayout
    private lateinit var rvSchedule: RecyclerView

    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM")

    private lateinit var dateList: List<LocalDate>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        tabDays = findViewById(R.id.tabDays)
        rvSchedule = findViewById(R.id.rvSchedule)
        rvSchedule.layoutManager = LinearLayoutManager(this)

        val startDate = LocalDate.now() // lub określona data, np. LocalDate.of(2025, 1, 10)
        val endDate = LocalDate.of(2025, 6, 24)
        dateList = getDateRange(startDate, endDate)

        tabDays.tabMode = TabLayout.MODE_SCROLLABLE

        dateList.forEach { date ->
            val formattedDate = date.format(dateFormatter)
            tabDays.addTab(tabDays.newTab().setText(formattedDate))
        }

        if (dateList.isNotEmpty()) {
            updateScheduleForDate(dateList[0])
        }

        tabDays.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position ?: 0
                if (position in dateList.indices) {
                    updateScheduleForDate(dateList[position])
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun updateScheduleForDate(date: LocalDate) {
        val dayOfWeek: DayOfWeek = date.dayOfWeek
        val scheduleForDay = weeklySchedule[dayOfWeek] ?: emptyList()
        rvSchedule.adapter = ScheduleAdapter(scheduleForDay)
    }

    private fun getDateRange(start: LocalDate, end: LocalDate): List<LocalDate> {
        val days = ChronoUnit.DAYS.between(start, end).toInt()
        return (0..days).map { start.plusDays(it.toLong()) }
    }
}
