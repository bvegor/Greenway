package fr.Egor.greenwaywork.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.Egor.greenwaywork.*

class VeloAdapter(
    val context: MainActivity,
    private val veloList: List<VeloModel>,
    private val layoutId: Int
    ): RecyclerView.Adapter<VeloAdapter.ViewHolder>(){
    //boite pour ranger tous les composants a controler
    // on passe la vue a controller RecyclerView
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
        // image
        val veloImage = view.findViewById<ImageView>(R.id.image_item)
        val veloName:TextView? = view.findViewById(R.id.name_item)
        val veloDescription:TextView? = view.findViewById(R.id.description_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)
    }

    // permets d'injecter notre layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater
           .from(parent.context)
           .inflate(layoutId, parent,false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //récuper les infos
       val currentVelo = veloList[position]

        //récuperer le repository
        val repo = BikeRepository()

        //utiliser glide pour récuperer l'image de son lien ->
       Glide.with(context).load(Uri.parse(currentVelo.imageUrl)).into(holder.veloImage)

        //mettre a jour le nom
        holder.veloName?.text = currentVelo.name

        //maj longi
        holder.veloDescription?.text = currentVelo.longitude

        //vérifier si ca a été liké
        if(currentVelo.liked){
            holder.starIcon.setImageResource(R.drawable.ic_uncharged)
        }
        else{
            holder.starIcon.setImageResource(R.drawable.ic_charged)
        }
        holder.starIcon.setOnClickListener{
        }
        //intercation lors du clic
        holder.itemView.setOnClickListener{
            //afficher la popup
            VeloPopup(this, currentVelo).show()
        }
    }

    override fun getItemCount(): Int = veloList.size
}