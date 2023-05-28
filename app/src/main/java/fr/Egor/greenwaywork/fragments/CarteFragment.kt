package fr.Egor.greenwaywork.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import fr.Egor.greenwaywork.MainActivity
import fr.Egor.greenwaywork.BikeRepository.Singleton.veloList
import fr.Egor.greenwaywork.R
import fr.Egor.greenwaywork.R.*
import fr.Egor.greenwaywork.adapter.VeloAdapter
import fr.Egor.greenwaywork.adapter.VeloItemDecoration
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.*

var idBat = "000001"
var longUrl: String = "";
var latUrl: String  = "";

class CollectionFragment(
    private val context: MainActivity
) : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val view = inflater?.inflate(layout.fragment_carte, container, false)
        requestHttp()

        //recuperer ma recyclerview
        val collectionRecyclerView = view?.findViewById<RecyclerView>(R.id.collection_recycler_list)
        collectionRecyclerView?.adapter =
            VeloAdapter(context, veloList.filter { it.liked }, layout.item_vertical_velo)
        collectionRecyclerView?.layoutManager = LinearLayoutManager(context)

        //marge espacement
        collectionRecyclerView?.addItemDecoration(VeloItemDecoration())



        val openMaps = view?.findViewById<Button>(R.id.maps_button)
        openMaps?.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.google.com/maps/search/?api=1&query=$longUrl,$latUrl")
            startActivity(openURL)

        }
        return view

    }


    val database = FirebaseDatabase.getInstance()
    private val client = OkHttpClient()

    fun requestHttp() {
        //http request
        val request = Request.Builder()
            .url("https://api.swx.altairone.com/spaces/projectiotjf/categories/Velos/things-status/01GVYZZKC2BYW63F48WG3MMQRH")
            .header("Authorization", "Bearer ory_at_tvDDzpevpqf7T2jf0TyYx0FmDNKpmv8xTVIT1CRwD-k.305s_zgXtVFThmEJKNJqlMmeb9SDNYhfLVkX9c9r7t4")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    for ((name, value) in response.headers) {
                        println("$name: $value")
                    }
                    val infos = (response.body!!.string())
                    println(infos)

                    //split infos
                    val json = JSONObject(infos)

                    val collection = json.getString("collection")
                    val description = json.getString("description")
                    val autonomieBatterie = json.getJSONObject("properties").getInt("Autonomie_batterie")
                    val latitude = json.getJSONObject("properties").getInt("Latitude")
                    val longitude = json.getJSONObject("properties").getString("Longitude")
                    val space = json.getString("space")
                    val title = json.getString("title")
                    val uid = json.getString("uid")


                    latUrl = json.getJSONObject("properties").getString("Latitude")
                    longUrl = json.getJSONObject("properties").getString("Longitude")


                    println("collection: $collection")
                    println("description: $description")
                    println("autonomie_batterie: $autonomieBatterie")
                    println("latitude: $latitude")
                    println("longitude: $longitude")
                    println("space: $space")
                    println("title: $title")
                    println("uid: $uid")

                    //maj bdd
                    val myRef = database.getReference("velos/velo1/")
                    val data = HashMap<String, Any>()
                    data["id"] =  "velo1"
                    data["imageUrl"] = "https://www.linkpicture.com/q/velo_001.png"
                    data["name"] =  "Vélo 001"
                    data["niveauBat"] = "$autonomieBatterie %"
                    data["latitude"] = "$latUrl"
                    data["longitude"] = "$longUrl"
                    data["idBat"] = "$idBat"
                    if (autonomieBatterie < 15){
                        data["liked"] = true
                    }
                    else{
                        data["liked"] = false
                    }
                    myRef.setValue(data)


                }
            }
        })





    }






}