package com.sudoavi.digitalbag

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        have_acc.setOnClickListener { startActivity(Intent(this,Login::class.java))
            finish()}

        auth = Firebase.auth
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        Signup_btn.setOnClickListener {
            val email = Usr_mail.text.toString()
            val phone = Usr_Phone.text.toString()
            val password = Usr_pass.text.toString()
            val Cpassword = Usr_cpass.text.toString()
            val name = Usr_name.text.toString()

            if (Cpassword == password) {
                if (Noerror(email, password, name, phone)) {
                    val user = hashMapOf(
                        "Name" to name,
                        "Phone" to phone,
                        "Email" to email
                    )

                    val Users = db.collection("USERS")

                    val qry = Users.whereEqualTo("Email",email).get().addOnSuccessListener { it ->
                        if(it.isEmpty()){
                            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                                    task ->
                                if(task.isSuccessful){
                                    Users.document(email).set(user)
                                    startActivity(Intent(this,Home::class.java).putExtra("Email",email))
                                    finish()

                                }
                                else{
                                    Toast.makeText(
                                        baseContext, "Authentication Failed !",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                        else{
                            Toast.makeText(
                                baseContext, "User Already Registered !",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this,Login_SignUp::class.java))

                        }
                    }

                }
                else{
                    Toast.makeText(
                        baseContext, "Enter The Details !",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else{
                Toast.makeText(
                    baseContext, "Confirm Password didn't match !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun Noerror(email:String,pass:String,name:String,phone:String): Boolean {
        return (email.trim{it<=' '}.isNotEmpty() && pass.trim{it<=' '}.isNotEmpty() && name.trim{it<=' '}.isNotEmpty() && phone.trim{it<=' '}.isNotEmpty())
    }
}