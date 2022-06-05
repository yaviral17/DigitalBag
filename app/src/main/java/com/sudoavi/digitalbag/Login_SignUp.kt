package com.sudoavi.digitalbag

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class Login_SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_sign_up)

        loginBtn.setOnClickListener {
            startActivity(Intent(this,Login::class.java))
        }

        signUpBtn.setOnClickListener {
            startActivity(Intent(this,SignUp::class.java))
        }

    }
}