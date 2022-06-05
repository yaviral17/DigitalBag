package com.sudoavi.digitalbag

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_upload_notes.*

@Suppress("DEPRECATION")
class UploadNotes : AppCompatActivity() {

    val PDF :Int = 0
    var uri : Uri? = null
    private lateinit var mStorage : StorageReference
//    private val user_mail = intent.getStringExtra("Usr_Email").toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_notes)

        mStorage = FirebaseStorage.getInstance().reference

        select_a_file_btn.setOnClickListener{
            view : View? -> val intent = Intent()
            intent.setType("application/pdf")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent,"Select PDF "),PDF)
        }

        uploadFileBtn.setOnClickListener {
            if (File_name.text.toString().isNotEmpty()){
                if(uri != null){
                    upload()
                }
                else{
                    Toast.makeText(this, "Select a file !!", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this, "Enter the file name", Toast.LENGTH_SHORT).show()
            }

        }
    }

    @SuppressLint("SetTextI18n")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PDF && resultCode== RESULT_OK){
            uri = data!!.data!!
            select_a_file_btn.text = "File selected click here to select again"
        }

    }

    private fun upload() {
        if(Loading_animation.visibility != View.VISIBLE) {
            val mRefrence = mStorage.child(
                intent.getStringExtra("Usr_Email").toString() + "/" + File_name.text + ".pdf"
            )
            animationView.visibility = View.GONE
            Loading_animation.visibility = View.VISIBLE
            mRefrence.putFile(uri!!).addOnCompleteListener { task ->
                if (task.isSuccessful) run {
                    Toast.makeText(this, "File Uploaded Successfully", Toast.LENGTH_SHORT).show()
                    animationView.visibility = View.VISIBLE
                    Loading_animation.visibility = View.GONE
                }
            }
                .addOnFailureListener {
                    Toast.makeText(this, "File Uploaded Failed", Toast.LENGTH_SHORT).show()
                    animationView.visibility = View.VISIBLE
                    Loading_animation.visibility = View.GONE
                }
        }else{
            Toast.makeText(this, "Please wait uploading ...", Toast.LENGTH_SHORT).show()
        }
    }
}