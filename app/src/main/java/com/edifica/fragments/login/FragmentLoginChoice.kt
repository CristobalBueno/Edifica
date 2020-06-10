package com.edifica.fragments.login

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.edifica.R
import com.edifica.activities.login.ActivityAccount
import com.edifica.activities.login.ActivityLogin
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login_choice.*
import render.animations.Attention
import render.animations.Render

/**
 * A simple [Fragment] subclass.
 */
class FragmentLoginChoice : Fragment() {

    private var animation = 0
    private lateinit var button_login: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_choice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image_right.alpha = 0F
        image_left.alpha = 0F
        text_choise_client.alpha = 0F
        text_choise_business.alpha = 0F

        button_login = view.findViewById(R.id.button_count)
        button_login.visibility = View.INVISIBLE

        animationArrow(view)
    }

    private fun animationArrow(view: View) {
        // Declare ImageView
        val image_right: ImageView = view.findViewById(R.id.image_right)
        val image_left: ImageView = view.findViewById(R.id.image_left)


        // Create Render Class
        val render = Render(requireContext())

        button_count.setOnClickListener {
            (activity as ActivityLogin).gotoActivity(ActivityAccount())
        }

        val timer = object : CountDownTimer(4000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                when (animation) {

                    1 -> {
                        // Set Animation
                        image_left.alpha = 1F
                        text_choise_client.alpha = 1F

                        render.setAnimation(Attention().Tada(image_left))
                        render.setDuration(1000)
                        render.start()
                    }
                    2 -> {
                        // Set Animation
                        image_right.alpha = 1F
                        text_choise_business.alpha = 1F

                        render.setAnimation(Attention().Tada(image_right))
                        render.setDuration(1000)
                        render.start()
                    }
                    3 -> {
                        button_login.visibility = View.VISIBLE
                    }

                }
                animation += 1
            }

            override fun onFinish() {
                image_right.alpha = 1F
                image_left.alpha = 1F
            }
        }
        timer.start()
    }

}
