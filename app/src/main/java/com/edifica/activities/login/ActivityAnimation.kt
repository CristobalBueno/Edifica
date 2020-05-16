package com.edifica.activities.login

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.edifica.R
import com.edifica.abstract.BaseActivity
import com.edifica.activities.business.ActivityBusinessMain
import com.edifica.activities.clients.ActivityClientMain
import com.edifica.models.DBAccess
import com.edifica.models.Dataholder
import com.edifica.models.Token
import com.edifica.models.Token.Companion.readToken
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_animation.*
import render.animations.Attention
import render.animations.Flip
import render.animations.Render
import render.animations.Zoom
import java.io.File

class ActivityAnimation : BaseActivity() {

    var animation: Int = 0
    var token: Boolean = false
    lateinit var userToken: Token

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        text_slogan.visibility = View.GONE

        loadUser()

        animationLogo()
    }

    fun animationLogo() {
        // Declare TextView
        val image: ImageView = findViewById(R.id.image_logo)
        val textSlogan: TextView = findViewById(R.id.text_slogan)

        // Create Render Class
        val render = Render(this)

        val timer = object : CountDownTimer(4000, 1100) {

            override fun onTick(millisUntilFinished: Long) {
                when (animation) {
                    0 -> {
                        // Set Animation
                        render.setAnimation(Zoom().In(image))
                        render.setDuration(1000)
                        render.start()
                    }
                    1 -> {
                        render.setAnimation(Attention().Tada(image))
                        render.setDuration(1000)
                        render.start()
                    }
                    2 -> {
                        like_text.likeAnimation()
                        text_slogan.visibility = View.VISIBLE
                        render.setAnimation(Flip().InX(textSlogan))
                        render.setDuration(1000)
                        render.start()
                    }
                }
                animation += 1
            }

            override fun onFinish() {
                animation = 0

                if (getLogin()) {
                    if (userToken.identifier == 0) {
                        gotoActivity(ActivityClientMain())
                    } else if (userToken.identifier == 1) {
                        gotoActivity(ActivityBusinessMain())
                    } else {
                        gotoActivity(ActivityLogin())
                    }
                } else {
                    gotoActivity(ActivityLogin())
                }
            }
        }
        timer.start()
    }

    fun getLogin(): Boolean {
        if (token) {
            return true
        }

        return false
    }

    // TODO Cambiarlo a otro lugar (segundo plano)
    fun loadUser() {
        val user = FirebaseAuth.getInstance().currentUser
        val file = File(filesDir, Dataholder.FILENAME)

        if (user != null) {
            userToken = readToken(file)
            Log.e("debug", userToken.toString())
            if (userToken.uid == user.uid) {
                token = true
                // TODO Actualizar datos de la base de datos
                var dbUser = DBAccess.loadUser()
                Log.e("debug", dbUser.toString())
                if (dbUser != null) {
                    userToken.name = dbUser.name
                    userToken.phone = dbUser.phone
                    userToken.identifier = dbUser.identifier
                    userToken.email = dbUser.email
                    Log.e("debug", userToken.toString())
                }
            }
        }
    }
}
