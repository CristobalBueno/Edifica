package com.edifica.activities.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.edifica.R
import com.edifica.abstract.BaseActivity
import com.edifica.fragments.login.FragmentLogin
import com.edifica.fragments.login.FragmentLoginChoice
import kotlinx.android.synthetic.main.activity_login.*

class ActivityLogin : BaseActivity() {

    val ISCLIENT = "isClient"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewPage()
    }

    fun viewPage() {

        val bundle = Bundle()

        //Para que tenga sombra o no (por ello, la elevación)
        supportActionBar?.elevation = 0.0f

        //Aquí relacionamos cada una de las posiciones de las pestaña, con   nuestros respectivos Fragments.
        val myAdapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> {
                        bundle.putBoolean(ISCLIENT, true)
                        val fragLoginClient = FragmentLogin()
                        fragLoginClient.arguments = bundle
                        fragLoginClient
                    }
                    1 -> {
                        FragmentLoginChoice()
                    }
                    2 -> {
                        bundle.putBoolean(ISCLIENT, false)
                        val fragLoginClient = FragmentLogin()
                        fragLoginClient.arguments = bundle
                        fragLoginClient
                    }
                    else -> {
                        FragmentLoginChoice()
                    }
                }
            }

            //Número de ítems que mostrará, en este caso 3
            override fun getItemCount(): Int {
                return 3
            }
        }

        //Instanciamos el diseño.
        mainViewPager2.adapter = myAdapter
        indicator.setViewPager(mainViewPager2)

        //Esta línea es para que cuando arranque, aparezca en una pestalla en  particular y con el efecto de inicio o no:
        mainViewPager2.setCurrentItem(1, false)
    }

}

