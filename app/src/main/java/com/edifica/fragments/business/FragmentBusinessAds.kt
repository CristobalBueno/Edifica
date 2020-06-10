package com.edifica.fragments.business

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.edifica.activities.business.ActivityBusinessAdsDetails
import com.edifica.R
import com.edifica.activities.business.ActivityBusinessMain
import com.edifica.adapters.SearchAdsAdapter
import com.edifica.interfaces.CustomAdsListener
import com.edifica.models.Ads
import com.edifica.models.Dataholder
import com.edifica.models.User
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_business_ads.*
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class FragmentBusinessAds : Fragment(), CustomAdsListener {


    private lateinit var adsAdapter: SearchAdsAdapter
    var db = FirebaseFirestore.getInstance()
    var allAds = arrayListOf<Ads>()
    val ADV = "adv"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_business_ads, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as ActivityBusinessMain).setSupportActionBar(topSearchAppBar)
        setHasOptionsMenu(true)
        topSearchAppBar.title = " "
        search()
    }

    override fun onStart() {
        super.onStart()

        allAds = loadAllAdsDataBase()
    }

    override fun onItemAdsClick(currentAds: Ads, position: Int) {
        var intent: Intent = Intent(context, ActivityBusinessAdsDetails::class.java)
        intent.putExtra(ADV, currentAds)

        startActivity(intent)
    }

    fun search() {
        try {
            mainRecyclerSeachAds.layoutManager = LinearLayoutManager(context)
            adsAdapter = SearchAdsAdapter(allAds, this)
            mainRecyclerSeachAds.adapter = adsAdapter
            mainRecyclerSeachAds.visibility = View.VISIBLE
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_search, menu)
        val searchItem = menu.findItem(R.id.ic_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.v("miapp", "Cuando pulso el boton de envio del teclado")
                query?.let { adsAdapter.filter(it) }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                Log.v("miapp", "Cuando escribo")
                query?.let { adsAdapter.filter(it) }
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun loadAllAdsDataBase(): ArrayList<Ads> {
        val query = db.collection("ads")
        var dbAllAds: ArrayList<Ads> = arrayListOf()

        query.get().addOnSuccessListener { documents ->
            if (documents != null) {
                for (document in documents) {

                    var user: User?
                    var userAux: DocumentReference = document["user"] as DocumentReference
                    var add: Ads = document.toObject(Ads::class.java)

                    Log.e("e", (document["user"] as DocumentReference).id )
                    userAux.get().addOnCompleteListener{
                        user = it.result?.toObject(User::class.java)

                        Log.e("e", user.toString())
                        if (user != null) {
                            add.user = user!!
                        }
                        dbAllAds.add(add)
                        search()
                    }
                }

            } else {
                Log.d("loadAllAdsDataBase", "no such document")
            }
        }.addOnFailureListener { exception ->
            Log.d("loadAllAdsDataBase", "get failed with", exception)
        }
        return dbAllAds
    }

}
