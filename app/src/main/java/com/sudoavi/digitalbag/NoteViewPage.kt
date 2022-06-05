package com.sudoavi.digitalbag

import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.loader.content.AsyncTaskLoader
import com.airbnb.lottie.animation.content.Content
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_note_view_page.*
import java.io.File
import java.io.InputStream
import java.net.URL

class NoteViewPage : AppCompatActivity() {
    private lateinit var fileUrl : String
    private lateinit var DfileName : String
    private lateinit var SfileName : String
    private lateinit var mStorage : StorageReference
    var pageNo = 0

//    val prefixURL = "https://docs.google.com/viewer?embedded = true&url = "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_view_page)


        DfileName = intent.getStringExtra("dFname").toString()
        SfileName = intent.getStringExtra("sFname").toString()

        mStorage = FirebaseStorage.getInstance().reference

    if (DfileName != "null"){
        mStorage.child(Home.shared_user+"/"+DfileName+".pdf").delete().addOnSuccessListener {
            Toast.makeText(this, "File deleted successfully...", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,Home::class.java))
            finish()
        }
    }else{
        downloadPDF(SfileName)
    }





//            fileUrl = it.toString()
//            File_name.text = intent.getStringExtra("fName")
//            downloadPDF()
////            readPdf()
//        }
//            .addOnFailureListener { Toast.makeText(this, "Unable to load pdf", Toast.LENGTH_SHORT).show() }
//
//        download_file.setOnClickListener {
//           downloadPDF()
//        }
//        delete_file.setOnClickListener {
//            mStorage.child(Home.shared_user+"/"+fileName+".pdf").delete().addOnCompleteListener {
//                Toast.makeText(this, "File deleted successfully...", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this,Home::class.java))
//                finish()
//            }
//        }

        startActivity(Intent(this,Home::class.java))
        finish()

    }


    private fun downloadPDF(fileName:String){
        mStorage.child(Home.shared_user+"/"+fileName+".pdf").downloadUrl.addOnSuccessListener {
            Toast.makeText(this, "Downloading...", Toast.LENGTH_SHORT).show()
            val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(it.toString() + ""))
            request.setTitle(fileName)
            request.setMimeType("application/pdf")
            request.allowScanningByMediaScanner()
            request.setAllowedOverMetered(true)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            //            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName+".pdf")
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                fileName + ".pdf"
            )
            val dm: DownloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
        }
    }




}