package com.edifica.activities.business

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.edifica.R
import com.edifica.adapters.BusinessBudgetsAdapter
import com.edifica.models.Ads
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.practica.proyect_no_name.Interface.AdListener
import kotlinx.android.synthetic.main.activity_business_profile_budgets.*

class ActivityBusinessProfileBudgets : AppCompatActivity() , AdListener {

    lateinit var mAdapter: BusinessBudgetsAdapter
    var works: ArrayList<Ads> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_profile_budgets)

        mAdapter = BusinessBudgetsAdapter(works, this)
        budgets_RecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        budgets_RecyclerView.adapter = mAdapter
    }
    override fun deleteAd(adSelected: Ads) {
        val dialogBuilder = MaterialAlertDialogBuilder(this, R.style.MyMaterialAlertDialog)
        dialogBuilder.setMessage(R.string.activity_business_profile_budgets_question_alert_dialog)
            .setPositiveButton(R.string.activity_business_profile_budgets_affirmative_alert_dialog) { _, _ ->
                works.remove(adSelected)
                mAdapter.notifyDataSetChanged()
            }
            .setNegativeButton(R.string.activity_business_profile_budgets_negative_alert_dialog) { dialog, _ ->
                dialog.cancel()
            }

        val alert = dialogBuilder.create()
        alert.setTitle(R.string.activity_business_profile_budgets_action_alert_dialog)
        alert.show()
    }
}
