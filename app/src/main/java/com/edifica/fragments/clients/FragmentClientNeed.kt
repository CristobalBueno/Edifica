package com.edifica.fragments.clients

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edifica.R
import com.edifica.activities.clients.ActivityClientFormulary
import com.edifica.models.Guild
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.practica.proyect_no_name.Adapter.GridAdapter
import com.practica.proyect_no_name.Interface.GridCustomListener
import kotlinx.android.synthetic.main.fragment_client_need.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentClientNeed : Fragment(), GridCustomListener {

    var guilds = arrayOf<Guild>()
    lateinit var floatingButton: FloatingActionButton
    val SELECTEDGUILDS = "selectedGuilds"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View =
            inflater.inflate(R.layout.fragment_client_need, container, false)

        var recyclerView = rootView.findViewById<RecyclerView>(R.id.clientRecyclerView)
        floatingButton =
            rootView.findViewById<FloatingActionButton>(R.id.clientFloatingActionButton)
        floatingButton.visibility = View.INVISIBLE

        val mAdapter = GridAdapter(generateGuilds(), this)
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

    fun generateGuilds(): Array<Guild> {
        for (guild in enumValues<Guild.GuildName>().iterator()) {
            val integer =
                resources.getIdentifier(guild.image.toLowerCase(), "drawable", context?.packageName)
            val image = resources.getDrawable(integer)

            var guild = Guild(guild.guildName, image, false)
            guilds += guild
        }

        return guilds
    }

    override fun onItemClick(guild: Guild, position: Int) {
        guilds[position] = guild

        var number = guilds.filter { it.isChecked == true }

        if (number.size > 0) {
            floatingButton.visibility = View.VISIBLE
        } else {
            floatingButton.visibility = View.INVISIBLE
        }
    }

}
