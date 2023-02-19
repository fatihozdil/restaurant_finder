package com.example.kt.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kt.R
import com.example.kt.User
import com.google.firebase.database.FirebaseDatabase


class RegistrationActivity : AppCompatActivity() {
    lateinit var etEmail : EditText
    lateinit var etConfPass : EditText
    lateinit var etFullName : EditText
    private lateinit var etPass : EditText
    private lateinit var btnSignUp : Button

    //create firebase object
    var database: FirebaseDatabase? = FirebaseDatabase.getInstance("https://restayrantado-default-rtdb.firebaseio.com")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        etEmail = findViewById(R.id.etEmail)
        etFullName = findViewById(R.id.etFullName)
        etConfPass = findViewById(R.id.etConfPass)
        etPass = findViewById(R.id.etPass)
        btnSignUp = findViewById(R.id.btnSignUp1)

        btnSignUp.setOnClickListener {
            signUpUser()
        }
    }
    private fun signUpUser() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        val name = etFullName.text.toString()
        val confirmPassword = etConfPass.text.toString()

        // check pass
        if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        if (pass != confirmPassword) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return
        }
        val myRef = database!!.getReference("user/")

        val user = User(email,name, pass)
        myRef.push().setValue(user).addOnCompleteListener(this) {

            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
            }

            // clear inputs
            etFullName.text.clear()
            etConfPass.text.clear()
            etPass.text.clear()
            etEmail.text.clear()
        }


    }

    fun login(view: View?) {
        startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
    }
}