package com.edifica.activities.clients

import android.os.Bundle
import android.util.Log
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.edifica.R
import com.edifica.abstract.BaseActivity
import com.edifica.models.Ads
import com.edifica.models.Dataholder
import com.edifica.models.Guild
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.practica.proyect_no_name.Adapter.FormularySpinnerAdapter
import kotlinx.android.synthetic.main.activity_client_formulary.*
import java.util.*
import kotlin.collections.ArrayList


class ActivityClientFormulary : BaseActivity() {

    lateinit var guilds : BooleanArray
    val SELECTEDGUILDS = "selectedGuilds"
    lateinit var spinnerSettlement : Spinner
    lateinit var spinnerProvince: Spinner
    var db = FirebaseFirestore.getInstance()
    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_formulary)

        if (intent != null) {
            guilds = intent.getBooleanArrayExtra(SELECTEDGUILDS)!!
        }

        spinnerSettlement = findViewById<Spinner>(R.id.formulary_spinner_Settlement)
        spinnerSettlement.adapter = FormularySpinnerAdapter(this, R.id.formulary_tex_spinner_row, Dataholder.spinnerSettlement)


        spinnerProvince = findViewById<Spinner>(R.id.formulary_spinner_Province)
        spinnerProvince.adapter = FormularySpinnerAdapter(this, R.id.formulary_tex_spinner_row, Dataholder.spinnerProvince)

        // TODO Terminar de poner las fotos y guardar en base de datos. Despues enviar a la activity Main.

        formulary_button.setOnClickListener {
            sendAds()
        }
    }

    fun sendAds (){
        val text_info = editText.text.toString()
        val text_province = spinnerProvince.getSelectedItem().toString()
        val text_settlement = spinnerSettlement.getSelectedItem().toString()
        var guildarray: ArrayList<String> = arrayListOf()
        var newAds = Ads()

        newAds.formInfo = text_info
        newAds.province = text_province
        newAds.settlement = text_settlement
        newAds.images = arrayListOf("f", "ff")

        loadArrayGuild(guildarray)

        val data = hashMapOf(
            "formInfo" to newAds.formInfo,
            "guilds" to guildarray,
            "images" to newAds.images,
            "province" to newAds.province,
            "settlement" to newAds.settlement,
            "user" to "/users/"+ (auth.currentUser?.uid ?: 0)
        )

        Log.w("miapp", "valores recogidos $text_info, $text_settlement , $text_province,  ${guildarray}")

        db.collection("ads")
            .add(data)
            .addOnSuccessListener { documentReference ->
                gotoActivity(ActivityClientMain())
                //Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                //Log.w(TAG, "Error adding document", e)
            }
    }

    fun loadArrayGuild(guildarray: ArrayList<String>){
        var position = 0
        for (guild in guilds) {
            if (guild){
                when(position){
                    0 -> guildarray.add("Carpintero")
                    1 -> guildarray.add("Pintores")
                    2 -> guildarray.add("Albañiles")
                    3 -> guildarray.add("Electricistas")
                    4 -> guildarray.add("Fontaneros")
                    5 -> guildarray.add("Escayolistas")
                    6 -> guildarray.add("Carpinteria metálica")
                }
            }
            position += 1
        }
    }

}