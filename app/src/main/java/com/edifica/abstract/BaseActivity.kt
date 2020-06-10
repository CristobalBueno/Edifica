package com.edifica.abstract

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.edifica.R
import java.io.Serializable

abstract class BaseActivity : AppCompatActivity() {
    val OBJECT = "object"

    fun gotoFragment(myfragment: Fragment, toBack: Boolean = false) {
        val transaction =
            supportFragmentManager.beginTransaction().replace(R.id.mainViewPager2, myfragment)

        if (toBack) {
            transaction.addToBackStack(myfragment.toString())
        }
        transaction.commit()
    }

    //TODO HE IMPLEMENTADO NUEVA FUNCIONALIDAD PARA MANDAR OBJETOS, DE PARTIDA NO SE ACTIVA PARA NO FASTIDIAR LO ANTERIOR.
    fun gotoActivity(myActivity: Activity, withputExtra: Boolean = false, serializable: Serializable? = null) {
        val intent = Intent(this, myActivity::class.java)
        if (withputExtra){
            intent.putExtra(OBJECT, serializable)
        }
        startActivity(intent)
    }
}
