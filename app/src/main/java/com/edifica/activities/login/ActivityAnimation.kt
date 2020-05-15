package com.edifica.activities.login

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.edifica.R
import com.edifica.abstract.BaseActivity
import com.edifica.activities.clients.ActivityClientMain
import com.edifica.models.Token
import kotlinx.android.synthetic.main.activity_animation.*
import render.animations.Attention
import render.animations.Flip
import render.animations.Render
import render.animations.Zoom

class ActivityAnimation : BaseActivity() {

    var animation: Int = 0
    var token: Token? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        text_slogan.visibility = View.GONE

        animationLogo()
    }

    fun animationLogo() {
        // Declare TextView
        val image: ImageView = findViewById(R.id.image_logo)
        var textSlogan: TextView = findViewById(R.id.text_slogan)

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
                // TODO Esto hay que cambiarlo
                animation = 0

                if (getLogin()) {
                    gotoActivity(ActivityClientMain())
                } else {
                    gotoActivity(ActivityLogin())
                }
            }
        }
        timer.start()
    }

    fun getLogin(): Boolean {
        if (token != null) {
            return true
        }

        return false
    }
}
