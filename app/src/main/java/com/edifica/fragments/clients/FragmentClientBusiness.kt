package com.edifica.fragments.clients

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.edifica.R
import com.edifica.activities.business.ActivityBusinessProfile
import com.edifica.activities.clients.ActivityClientMain
import com.edifica.adapters.SearchBusinessAdapter
import com.edifica.models.Business
import com.edifica.models.Dataholder
import com.practica.proyect_no_name.Interface.CustomItemListener
import kotlinx.android.synthetic.main.fragment_client_business.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentClientBusiness : Fragment(), CustomItemListener {
    private lateinit var businessAdapter: SearchBusinessAdapter

    // TODO SANTANA
    // TODO pasarlo a la base de datos
    // TODO Crea un metodo para la llamada a base de datos y obtener todos los business. Y... borra de Dataholder
    var allBusiness = Dataholder.business

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_business, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as ActivityClientMain).setSupportActionBar(topSearchAppBar)

        setHasOptionsMenu(true)
        topSearchAppBar.title = " "
        search()
    }

    override fun onItemClick(currentBusiness: Business, position: Int) {
        // TODO SANTANA
        // TODO pasarlo al activity correspondiente (pero bien echo)
        // TODO usa bundles/Intents capullo, nada de DATAHOLDERS
        Dataholder.currentBusinessProfile = currentBusiness

        (activity as ActivityClientMain).gotoActivity(ActivityBusinessProfile())
    }

    fun search() {
        mainRecyclerSeachBusiness.layoutManager = LinearLayoutManager(context)
        businessAdapter = SearchBusinessAdapter(allBusiness, this)
        mainRecyclerSeachBusiness.adapter = businessAdapter
        mainRecyclerSeachBusiness.visibility = View.VISIBLE
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_search, menu)
        val searchItem = menu.findItem(R.id.ic_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { businessAdapter.filter(it) }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let { businessAdapter.filter(it) }
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}
