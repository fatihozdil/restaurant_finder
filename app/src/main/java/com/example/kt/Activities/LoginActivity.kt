package com.example.kt.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kt.FavoritesRestaurantModel
import com.example.kt.Fragments.AccountFragment
import com.example.kt.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LoginActivity : AppCompatActivity() {
    lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    lateinit var btnLogin: Button
var favoritesArrayList = ArrayList<FavoritesRestaurantModel>()
    var database: FirebaseDatabase? =
        FirebaseDatabase.getInstance("https://restayrantado-default-rtdb.firebaseio.com")
    var myRef = database!!.getReference("user")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.btnLogin)
        etEmail = findViewById(R.id.etEmail)
        etPass = findViewById(R.id.etPassword)

        btnLogin.setOnClickListener {
            loginOperation()
        }

    }

    private fun loginOperation() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                var isUserExist = false;
                var isPasswordCorrect = false;
                var dbEmail = ""
                var dbPass = ""
                var dbName = ""

                for (ds in dataSnapshot.children) {
                    dbEmail = ds.child("email").getValue(String::class.java)!!
                    dbPass = ds.child("password").getValue(String::class.java)!!

                    if (dbEmail.equals(email)) {
                        isUserExist = true
                        if (dbPass.equals(pass)) {
                            isPasswordCorrect = true
                            dbName = ds.child("name").getValue(String::class.java)!!

                        }
                    }


                }
                if (!isUserExist) {
                    Toast.makeText(this@LoginActivity, "user not found", Toast.LENGTH_SHORT)
                        .show()
                    return
                } else {
                    if (!isPasswordCorrect) {
                        Toast.makeText(
                            this@LoginActivity,
                            "password is incorrect",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    } else {

                        Toast.makeText(
                            this@LoginActivity,
                            "Login successfull welcome $dbName",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)

                        intent.putExtra("name", dbName)
                        intent.putExtra("email", dbEmail)
                        intent.putExtra("favorites", favoritesArrayList)
                        startActivity(intent)
                    }
                }


            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Toast.makeText(this@LoginActivity, "Log In failed ", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun register(view: View?) {
        startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
    }

}