package com.sudoavi.digitalbag

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_home.*
import java.util.stream.DoubleStream.generate

class Home : AppCompatActivity() {

    private lateinit var upFileList:MutableList<String>
    private lateinit var mStorage : StorageReference
    private lateinit var email:String
    companion object{
        lateinit var shared_user:String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mStorage = FirebaseStorage.getInstance().reference
        urll.visibility = View.GONE
        //Session management
        email = "email"
        if(Loaddata()!="null"){
            email = Loaddata()
//            Toast.makeText(this, "If_1"+email, Toast.LENGTH_SHORT).show()

        }else{
            if (intent.getStringExtra("Email")==null){
                startActivity(Intent(this,Login_SignUp::class.java))
                finish()
            }
            else{
                email = intent.getStringExtra("Email").toString()
//                Toast.makeText(this, "if_2_2 "+email, Toast.LENGTH_SHORT).show()
                savedata(email)
            }
        }
        shared_user = email
        upFileList = mutableListOf()
        getFileList()

        RefreshBtn.setOnClickListener {
                getFileList()
        }
        LogoutBtn.setOnClickListener{
            val sharedPreferences = getSharedPreferences("Email", Context.MODE_PRIVATE)
            sharedPreferences.edit().remove("Email").apply()
            startActivity(Intent(this,Login_SignUp::class.java))
            finish()
        }


        add_subject.setOnClickListener {
            startActivity(Intent(this,UploadNotes::class.java).putExtra("Usr_Email",email))
        }

    }

    private fun Loaddata(): String {
        val sharedPreferences = getSharedPreferences("Email", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("Email",null )
        return savedString.toString()
    }

    private fun savedata(email : String) {
        val InsertedText = email
        val sharedPreferences = getSharedPreferences("Email", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putString("Email",InsertedText)
        }.apply()
    }

    private fun getFileList(){
        upFileList.clear()
        val listRef = mStorage.child(email)
        listRef.listAll()
            .addOnSuccessListener {
//                Toast.makeText(this, it.items.toString(), Toast.LENGTH_SHORT).show()
                var i = 0
                while (i<it.items.size){
                    val cfname = it.items[i].name
                    upFileList.add(cfname.subSequence(0,cfname.length - 4).toString())
                    urll.text = urll.text.toString() + '\n' +it.items[i].name
                    i+=1
                }
                urll.text = urll.text.toString() + '\n' + upFileList.toString()
                Recycler__view.adapter = FileAdaptor(Files = upFileList.toList())
                Recycler__view.layoutManager = LinearLayoutManager(this)
            }
            .addOnFailureListener {
                // Uh-oh, an error occurred!
                Toast.makeText(this, "Uh-oh, an error occurred!", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onRestart() {
        super.onRestart()
        getFileList()
    }


}