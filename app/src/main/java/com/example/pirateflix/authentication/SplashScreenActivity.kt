package com.example.pirateflix.authentication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.pirateflix.MainActivity
import com.example.pirateflix.R
import com.example.pirateflix.databinding.ActivitySplashScreenBinding
import com.sg.magicpaybusiness.utils.AppConstants
import com.sg.magicpaybusiness.utils.AppPrefs

class SplashScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        val isLogin=AppPrefs.getBooleanPref(AppConstants.IS_LOGIN,this)


        showImageAnimation()

        Handler().postDelayed({
            if(isLogin){
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()
                this@SplashScreenActivity.overridePendingTransition(R.anim.swipe_right_enter, R.anim.swipe_right_exit)
            }
            else{
                startActivity(Intent(this@SplashScreenActivity, LandingPageActivity::class.java))
                finish()
                this@SplashScreenActivity.overridePendingTransition(R.anim.swipe_right_enter, R.anim.swipe_right_exit)
            }


        }, 3200)


    }

    private fun showTextAnimation() {
        val animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, -10f,
            Animation.RELATIVE_TO_SELF, 0f
        )

        // Set duration and repeat count
        animation.duration = 1000 // Duration in milliseconds
        animation.repeatCount = 0 // Set to 0 if you don't want to repeat the animation

        // Apply bounce effect
        val bounceInterpolator = BounceInterpolator()
        animation.interpolator = bounceInterpolator

        // Start the animation
        binding.tvAppName.startAnimation(animation)
    }

    private fun showImageAnimation() {
        val durationMillis = 2000L
        val fadeInAnimation = AlphaAnimation(0.1f, 1.0f)
        fadeInAnimation.duration = durationMillis
        fadeInAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
               binding.ivLogo.isVisible=true
            }

            override fun onAnimationEnd(animation: Animation?) {
                showTextAnimation()
                binding.tvAppName.isVisible=true

            }

            override fun onAnimationRepeat(animation: Animation?) {
                // Animation repeated
            }
        })

        binding.ivLogo.startAnimation(fadeInAnimation)

    }
}