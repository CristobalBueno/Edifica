package com.edifica.abstract

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.edifica.R

abstract class BaseActivity : AppCompatActivity() {

    fun gotoFragment(myfragment: Fragment, toBack: Boolean = false) {
        val transaction =
            supportFragmentManager.beginTransaction().replace(R.id.mainViewPager2, myfragment)

        if (toBack) {
            transaction.addToBackStack(myfragment.toString())
        }
        transaction.commit()
    }

    fun gotoActivity(myActivity: Activity) {
        val intent = Intent(this, myActivity::class.java)
        startActivity(intent)
    }
}
