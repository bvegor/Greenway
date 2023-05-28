package fr.Egor.greenwaywork

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.Egor.greenwaywork.BikeRepository.Singleton.databaseRef
import fr.Egor.greenwaywork.BikeRepository.Singleton.veloList
// fr.Egor.greenwaywork.PlantRepository.Singleton.storageReference

class BikeRepository {


    //singleton acceder au valeurs sur toute application sans recreeer de liste
    object Singleton {
        //donner le lien pour acceder au bucket
       // private val BUCKET_URL: String = "gs://greenwaywork-d6975.appspot.com"

        //se connecter  a notre espace de stockage
        //val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        //se connecter a la réference "velos"

        val databaseRef = FirebaseDatabase.getInstance().getReference("velos")

        //créer une liste

        val veloList = arrayListOf<VeloModel>()
    }

    //demander quelles sont les valeurs dans pour pouvoir les injecter

    fun updateData(callback: () -> Unit){ //pour que l'appli charge une fois que les infoBDD récuperes
        //absorber les données depuis la databaseRef -> liste
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //retirer les anciennes valeurs
                veloList.clear()
                //récolter la liste
                for (ds in snapshot.children){
                    //construire un objet
                    val velo = ds.getValue(VeloModel::class.java)

                    //vérifier que l'objet n'est pas null
                    if(velo != null){
                        //ajouter l'objet a notre liste
                        veloList.add(velo)
                    }
                }
                //actionner le callback
                callback()
            }

            override fun onCancelled(error: DatabaseError) {
                //au cas ou il ne trouve pas les élements en question
            }

        })
    }


    //mettre a jour un objet en bdd
    fun updateVelo(velo: VeloModel){
        databaseRef.child(velo.id).setValue(velo)
    }

}