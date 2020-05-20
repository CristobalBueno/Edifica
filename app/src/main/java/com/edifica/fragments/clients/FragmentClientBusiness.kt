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
import com.google.firebase.firestore.FirebaseFirestore
import com.practica.proyect_no_name.Interface.CustomItemListener
import kotlinx.android.synthetic.main.fragment_client_business.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentClientBusiness : Fragment(), CustomItemListener {

    private lateinit var businessAdapter: SearchBusinessAdapter
    val TAG = "miapp"
    var db = FirebaseFirestore.getInstance()
    private var allBusiness = arrayListOf<User>()

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
    }

    override fun onStart() {
        super.onStart()
        allBusiness = loadAllBusinessDataBase()
    }

    override fun onItemClick(currentUser: User, position: Int) {
        //TODO CRISTOBAL
        (activity as ActivityClientMain).gotoActivity(
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

    fun loadAllBusinessDataBase(): ArrayList<User> {
        val query = db.collection("users").whereEqualTo("identifier", 1)
        var dbAllUsers: ArrayList<User> = arrayListOf()

        query.get().addOnSuccessListener { documents ->
            if (documents != null) {
                for (document in documents) {
                    var user: User = document.toObject(User::class.java)

                    dbAllUsers.add(user)
                }
                search()
            } else {
                Log.d(TAG, "no such document")
            }
        }.addOnFailureListener { exception ->
            Log.d(TAG, "get failed with", exception)
        }
        return dbAllUsers
    }
}
