package com.sudoavi.digitalbag

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

// Initialize Firebase Auth
        auth = Firebase.auth

        forget_pass.setOnClickListener {
            startActivity(Intent(this,PasswordReset::class.java))
        }

        login_btn.setOnClickListener {
            val email = Usr_nm.text.toString()
            val password = Usr_pass.text.toString()

            if (noError(email, password)) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            startActivity(Intent(this,Home::class.java).putExtra("Email",email))
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                baseContext, "Login failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }else{
                Toast.makeText(
                    baseContext, "Enter The Details !",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        CrAc.setOnClickListener {
            startActivity(Intent(this,SignUp::class.java))
            finish()
        }

    }

    private fun noError(name: String, pass: String): Boolean {
        return name.trim { it <= ' ' }.isNotEmpty() && pass.trim { it <= ' ' }
            .isNotEmpty()

    }
}