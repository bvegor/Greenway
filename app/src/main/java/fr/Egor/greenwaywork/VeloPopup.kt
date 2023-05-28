package fr.Egor.greenwaywork

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import fr.Egor.greenwaywork.adapter.VeloAdapter

class VeloPopup(private val adapter: VeloAdapter,
                private val currentVelo: VeloModel
                 ) : Dialog(adapter.context) {

    //injecter le layout popup_velo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //on ne veut pas de titre
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //injecter notre layout
        setContentView(R.layout.popup_velo_details)

        //initialise nos composants avec la bonne valeur
        setupComponents()
        setupCloseButton()
        setupStarButton()
    }

    //actualisation en temps réél
    private fun updateStar(button: ImageView){
        if (currentVelo.liked){
            button.setImageResource(R.drawable.ic_uncharged)
        }
        else {
            button.setImageResource(R.drawable.ic_charged)
        }

    }

    private fun setupStarButton() {
        //récupérer

        val starButton = findViewById<ImageView>(R.id.star_button)

        //remplacement du code redondant par la methode updateStar

        updateStar(starButton)




        //interaction avec la bdd

        starButton.setOnClickListener{
            currentVelo.liked = !currentVelo.liked
            val repo = BikeRepository()
            repo.updateVelo(currentVelo)
            updateStar(starButton)

        }
    }



    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_button).setOnClickListener{
            //fermer la fenetre popup
            dismiss()
        }
    }

    private fun setupComponents() {
     //actualiser l'image
        val veloImage = findViewById<ImageView>(R.id.image_item)
        Glide.with(adapter.context).load(Uri.parse(currentVelo.imageUrl)).into(veloImage)

        //actualiser le nom
        findViewById<TextView>(R.id.popup_velo_name).text = currentVelo.name

        //actualiser la description
        findViewById<TextView>(R.id.popup_velo_description_subtitle).text = currentVelo.latitude

        //actualiser le niveau de batterie
        findViewById<TextView>(R.id.popup_velo_subtitle).text = currentVelo.longitude

        //actualiser la localisation
        findViewById<TextView>(R.id.popup_bike_subtitle).text = currentVelo.niveauBat
        //actu idbat
        findViewById<TextView>(R.id.popup_id_batterie_subtitle).text = currentVelo.idBat
    }
}