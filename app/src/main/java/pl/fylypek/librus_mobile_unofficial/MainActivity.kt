package pl.fylypek.librus_mobile_unofficial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // Ustawienie nasłuchiwania pozycji w dolnej nawigacji
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_grades -> {
                    openFragment(GradesFragment())
                    true
                }
                R.id.nav_schedule -> {
                    openFragment(ScheduleFragment())
                    true
                }
                else -> false
            }
        }

        // Ustawienie domyślnego fragmentu
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.nav_grades
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}