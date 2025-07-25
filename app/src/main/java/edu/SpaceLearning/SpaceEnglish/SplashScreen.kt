package edu.SpaceLearning.SpaceEnglish

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val lottieView = findViewById<LottieAnimationView>(R.id.lottieAnimationView)

        lottieView.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                val user =  FirebaseAuth.getInstance().currentUser
                if(user == null) goToLoginActivity()
                else goToMainActivity()



            }
        })

        }

    private fun goToMainActivity() {
        // بعد نهاية الأنميشن، الانتقال إلى MainActivity
        startActivity(Intent(this@SplashScreen, MainActivity::class.java))
        finish()
    }

    private fun goToLoginActivity() {
        // بعد نهاية الأنميشن، الانتقال إلى MainActivity
        startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
        finish()
    }
    }