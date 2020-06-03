package com.edifica.fragments.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edifica.R
import com.edifica.activities.login.ActivityLogin
import com.edifica.models.Token
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentLogin : Fragment() {

    private var isValidName: Boolean = false
    private var isValidPhone: Boolean = false
    private var isValidEmail: Boolean = false
    private var isValidPassword = false

    private var isClient: Boolean? = false
    private  val ISCLIENT = "isClient"
    private var identifier: Int = 1
    private lateinit var userToken: Token

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

    private fun updateView() {

        val data = arguments
        isClient = data?.getBoolean(ISCLIENT)

        if (isClient == null) {
            isClient = false
        }

        textLayout_phone.hint = getString(R.string.login_client_textlayout_Phone)
        textLayout_email.hint = getString(R.string.login_client_textlayout_Email)
        textLayout_passworld.hint = getString(R.string.login_business_textlayout_Password)

        when (isClient) {
            true -> {
                text_title.text = getText(R.string.login_client_title)
                textLayout_name.hint = getString(R.string.login_client_textlayout_Name)

            }
            false -> {
                text_title.text = getText(R.string.login_business_title)
                textLayout_name.hint = getString(R.string.login_business_textlayout_Name)
            }
        }
    }

    private fun success_Registration() {
        nameVerification()
        emailVerification()
        phoneVerification()
        passwordVerification()

        if (isValidName && isValidPhone && isValidEmail && isValidPassword) {

            if (isClient == true) {
                identifier = 0
            } else {
                identifier = 1
            }

            userToken = Token(
                text_name.text.toString(),
                text_phone.text.toString(),
                text_email.text.toString(),
                text_password.text.toString(),
                "", identifier
            )

            (activity as ActivityLogin).createUser(userToken)
        }
    }

    private fun nameVerification() {
        if (!text_name.text.toString().isBlank() && text_name.text?.length!! <= 30) {
            isValidName = true
            textLayout_name.error = null
        } else {
            isValidName = false
            textLayout_name.error = getString(R.string.login_client_error_name)
        }
    }

    private fun emailVerification() {
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

    private fun phoneVerification() {
        if (android.util.Patterns.PHONE.matcher(text_phone.text.toString()).matches()) {
            isValidPhone = true
            textLayout_phone.error = null
        } else {
            isValidPhone = false
            textLayout_phone.error = getString(R.string.login_client_error_phone)
        }
    }

    private fun passwordVerification() {
        var password = text_password.text

        if (password?.length!! <= 16 && password.length >= 6) {
            isValidPassword = true
            textLayout_passworld.error = null
        } else {
            isValidPassword = false
            textLayout_passworld.error = getString(R.string.login_client_error_pass)
        }
    }
}
