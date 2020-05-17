package com.edifica.fragments.clients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edifica.R
import com.edifica.activities.clients.ActivityClientMain
import com.edifica.activities.login.ActivityAnimation
import com.edifica.models.Dataholder
import com.edifica.models.Token
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_client_need.*
import java.io.File

/**
 * A simple [Fragment] subclass.
 */
class FragmentClientNeed : Fragment() {

    val user = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_need, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        salir_boton.setOnClickListener{
            user.signOut()
            Token.deleteToken(File(activity?.filesDir, Dataholder.FILENAME))
            (activity as ActivityClientMain).gotoActivity(ActivityAnimation())
        }
    }

}
