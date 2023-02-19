package com.example.kt.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.kt.R
import android.content.Intent
import android.view.View
import com.example.kt.Activities.RegistrationActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_welcome)

    }

    fun register(view: View?) {
        startActivity(Intent(this@WelcomeActivity, RegistrationActivity::class.java))
    }

    fun login(view: View?) {
        startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
    }
}