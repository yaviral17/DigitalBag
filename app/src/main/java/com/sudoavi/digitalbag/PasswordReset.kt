package com.sudoavi.digitalbag

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_password_reset.*

class PasswordReset : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)


        button2.setOnClickListener {
            val emailAddress = USER_mail.text.toString()
            Firebase.auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "Email sent.")
                    Toast.makeText(this, "Email sent to $emailAddress", Toast.LENGTH_LONG)
                        .show()
                    startActivity(Intent(this, Login::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Enter a valid email ", Toast.LENGTH_LONG).show()

                }
            }
        }
    }
}