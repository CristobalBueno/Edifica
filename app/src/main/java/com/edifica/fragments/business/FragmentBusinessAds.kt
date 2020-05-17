package com.edifica.fragments.business

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
import kotlinx.android.synthetic.main.fragment_business_ads.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentBusinessAds : Fragment(), CustomAdsListener {
    private lateinit var adsAdapter: SearchAdsAdapter

    // TODO SANTANA
    // TODO LO REFERENTE A DATAHOLDER LO QUIERO FUERA, O LO COGEIS DE BASE DE DATOS O DE
    // TODO MEMORIA INTERNA
    var allAds = Dataholder.ads

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

    fun search(){
        mainRecyclerSeachAds.layoutManager = LinearLayoutManager(context)
        adsAdapter = SearchAdsAdapter(allAds,this)
        mainRecyclerSeachAds.adapter = adsAdapter
        mainRecyclerSeachAds.visibility = View.VISIBLE
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

    override fun onItemAdsClick(currentAds: Ads, position: Int) {
        // TODO SANTANA
        // TODO LO REFERENTE A DATAHOLDER LO QUIERO FUERA, O LO COGEIS DE BASE DE DATOS O DE
        // TODO MEMORIA INTERNA
        Dataholder.currentAds = currentAds
        (activity as ActivityBusinessMain).gotoActivity(ActivityBusinessAdsDetails())
    }

}
