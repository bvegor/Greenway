package fr.Egor.greenwaywork.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import fr.Egor.greenwaywork.MainActivity
import fr.Egor.greenwaywork.R
import java.net.HttpURLConnection
import java.net.URL

class ProcedureFragment(private val context: MainActivity) : Fragment() {

    private var ledState = false
    private var btnLed: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_procedure, container, false)

        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)
        pickupImageButton.setOnClickListener { wifiConnect() }

        btnLed = view.findViewById<Button>(R.id.btnLed)
        btnLed?.setOnClickListener { toggleLedButtonClick() }

        val database = FirebaseDatabase.getInstance()
        val editText = view.findViewById<EditText>(R.id.description_input)
        val confirmerButton = view.findViewById<Button>(R.id.confirm_button)
        confirmerButton?.setOnClickListener {
             idBat = editText?.text.toString()
            println(": $idBat")
            val reference = database.getReference("velos/velo1/")
            reference.child("idBat").setValue(idBat)
                .addOnSuccessListener {
                    // La mise à jour du champ1 a réussi
                }
                .addOnFailureListener {
                    // La mise à jour du champ1 a échoué
                }
        }

        return view
    }

    private fun wifiConnect() {
        startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
    }

    private fun toggleLedButtonClick() {
        ledState = !ledState

        val url = "http://192.168.4.1/26/${if (ledState) "on" else "off"}"

        Thread {
            try {
                val url = URL(url)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 5000
                connection.readTimeout = 5000

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // La requête GET a réussi
                } else {
                }

                connection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()

        btnLed?.text = if (ledState) "Éteindre la LED" else "Allumer la LED"
    }
}
