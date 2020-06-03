package com.edifica.fragments.clients

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.edifica.R
import com.edifica.activities.business.ActivityClientBusinessProfile
import com.edifica.activities.clients.ActivityClientMain
import com.edifica.adapters.SearchBusinessAdapter
import com.edifica.models.User
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.practica.proyect_no_name.Interface.CustomItemListener
import kotlinx.android.synthetic.main.fragment_client_business.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentClientBusiness : Fragment(), CustomItemListener {

    private lateinit var businessAdapter: SearchBusinessAdapter
    private lateinit var activityMain: ActivityClientMain

    private var allBusiness = arrayListOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activityMain = activity as ActivityClientMain

        return inflater.inflate(R.layout.fragment_client_business, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityMain.setSupportActionBar(topSearchAppBar)
        setHasOptionsMenu(true)
        topSearchAppBar.title = " "
    }

    override fun onStart() {
        super.onStart()

        allBusiness = activityMain.businessUsers
        search()
    }

    override fun onItemClick(currentUser: User, position: Int) {
        activityMain.gotoActivity(
            ActivityClientBusinessProfile(),
            true,
            currentUser
        )
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
