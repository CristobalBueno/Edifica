package com.edifica.activities.clients

import android.os.Bundle
import android.widget.Spinner
import com.edifica.R
import com.edifica.abstract.BaseActivity
import com.edifica.models.Dataholder
import com.practica.proyect_no_name.Adapter.FormularySpinnerAdapter

class ActivityClientFormulary : BaseActivity() {

    lateinit var guilds : BooleanArray
    val SELECTEDGUILDS = "selectedGuilds"
    lateinit var spinnerSettlement : Spinner
    lateinit var spinnerProvince: Spinner

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
    }
}