package com.edifica.fragments.login

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.edifica.R
import com.edifica.activities.client.ActivityClientMain
import com.edifica.activities.login.ActivityLogin
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentLogin : Fragment() {

    var isValidName: Boolean = false
    var isValidPhone: Boolean = false
    var isValidEmail: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateView()

        button_login.setOnClickListener {
            success_Registration()
        }
    }

    fun updateView() {

        val data = arguments
        val isClient = "isClient"

        when (data?.getBoolean(isClient)) {
            true -> {
                text_title.text = getText(R.string.login_client_title)
                textLayout_name.hint = getString(R.string.login_client_textlayout_Name)
                textLayout_phone.hint = getString(R.string.login_client_textlayout_Phone)
                textLayout_email.hint = getString(R.string.login_client_textlayout_Email)
            }
            false -> {
                text_title.text = getText(R.string.login_business_title)
                textLayout_name.hint = getString(R.string.login_business_textlayout_Name)
                textLayout_phone.hint = getString(R.string.login_business_textlayout_Phone)
                textLayout_email.hint = getString(R.string.login_business_textlayout_Email)
            }
        }
    }

    fun success_Registration() {
        nameVerification()
        emailVerification()
        phoneVerification()

        if (isValidName && isValidPhone && isValidEmail) {
            Toast.makeText(context, getString(R.string.login_client_success), Toast.LENGTH_LONG)
                .show()

            clientStart()
        }
    }

    //TODO Comprobar si hay que llevarlo a cliente o empresa
    //TODO descativar estas ventanas una vez que se ha registado

    fun clientStart() {
        Handler().postDelayed(Runnable {
            (activity as ActivityLogin).gotoActivity(ActivityClientMain())
        }, 2000)
    }

    fun nameVerification() {
        if (!text_name.text.toString().isBlank() && text_name.text?.length!! <= 30) {
            isValidName = true
            textLayout_name.error = null
        } else {
            isValidName = false
            textLayout_name.error = getString(R.string.login_client_error_name)
        }
    }

    fun emailVerification() {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(text_email.text.toString())
                .matches() && !text_email.text.toString().isBlank()
        ) {
            isValidEmail = true
            textLayout_email.error = null
        } else {
            isValidEmail = false
            textLayout_email.error = getString(R.string.login_client_error_email)
        }
    }

    fun phoneVerification() {
        Log.w(
            "miapp",
            "${android.util.Patterns.PHONE.matcher(text_phone.text.toString()).matches()}"
        )
        if (android.util.Patterns.PHONE.matcher(text_phone.text.toString()).matches()) {
            isValidPhone = true
            textLayout_phone.error = null
        } else {
            isValidPhone = false
            textLayout_phone.error = getString(R.string.login_client_error_phone)
        }
    }

}
