package fr.Egor.greenwaywork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.Egor.greenwaywork.fragments.ProcedureFragment
import fr.Egor.greenwaywork.fragments.CollectionFragment
import fr.Egor.greenwaywork.fragments.HomeFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        loadFragment(HomeFragment(this), R.string.home_page_title)

        //importer la navbar
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.home_page -> {
                    loadFragment(HomeFragment(this), R.string.home_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.carte_page -> {
                    loadFragment(CollectionFragment(this), R.string.collection_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.procedure_page -> {
                    loadFragment(ProcedureFragment(this), R.string.add_plant_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }


        }


    private fun loadFragment(fragment: Fragment, string: Int ) {

        val repo = BikeRepository()

        //actualiser le titre de la page
        findViewById<TextView>(R.id.page_title).text = resources.getString(string)

        //mettre a jour la liste
        repo.updateData{
            //injecter le fragment dans notre boite (fragment_container)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()

    }
}
}