package com.edifica.fragments.clients

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edifica.R
import com.edifica.activities.clients.ActivityClientFormulary
import com.edifica.activities.clients.ActivityClientMain
import com.edifica.models.Guild
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.practica.proyect_no_name.Adapter.GridAdapter
import com.practica.proyect_no_name.Interface.GridCustomListener
import kotlinx.android.synthetic.main.fragment_client_need.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentClientNeed : Fragment(), GridCustomListener {

    private lateinit var activityMain: ActivityClientMain
    private lateinit var guilds: Array<Guild>

    private lateinit var floatingButton: FloatingActionButton
    private val SELECTEDGUILDS = "selectedGuilds"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View =
            inflater.inflate(R.layout.fragment_client_need, container, false)

        activityMain = activity as ActivityClientMain
        guilds = activityMain.guilds

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.clientRecyclerView)
        floatingButton =
            rootView.findViewById<FloatingActionButton>(R.id.clientFloatingActionButton)
        floatingButton.visibility = View.INVISIBLE

        val mAdapter = GridAdapter(guilds, this)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = mAdapter

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clientFloatingActionButton.setOnClickListener {
            var booleanArray : BooleanArray = booleanArrayOf()

            for (guild in guilds) {
                booleanArray += guild.isChecked
            }

            val intent = Intent(context, ActivityClientFormulary::class.java)
            intent.putExtra(SELECTEDGUILDS, booleanArray)
            startActivity(intent)
        }
    }

    override fun onItemClick(guild: Guild, position: Int) {
        guilds[position] = guild

        var number = guilds.filter { it.isChecked }

        if (number.size > 0) {
            floatingButton.visibility = View.VISIBLE
        } else {
            floatingButton.visibility = View.INVISIBLE
        }
    }

}
