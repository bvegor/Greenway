package fr.Egor.greenwaywork.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.Egor.greenwaywork.BikeRepository.Singleton.veloList
import fr.Egor.greenwaywork.MainActivity
import fr.Egor.greenwaywork.R
import fr.Egor.greenwaywork.adapter.VeloAdapter
import fr.Egor.greenwaywork.adapter.VeloItemDecoration

//mise en place héritage , injection du layout
class HomeFragment(
    private val context: MainActivity
) : Fragment() {
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View? {

        val view =  inflater?.inflate(R.layout.fragment_home, container, false)

        //injecter sur homefragment le layout associé
        //recuper le recyclerview
        val horizontalRecyclerView = view?.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView?.adapter = VeloAdapter(context, veloList.filter {
        it.liked }, R.layout.item_horizontal_velo)

        //recuperer second recyclerview
        val verticalRecyclerView = view?.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView?.adapter = VeloAdapter(context, veloList, R.layout.item_vertical_velo)
        verticalRecyclerView?.addItemDecoration(VeloItemDecoration())
        return view
    }
}