package com.sudoavi.digitalbag

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_note_view_page.view.*
import kotlinx.android.synthetic.main.subject_view.view.*
import kotlinx.android.synthetic.main.subject_view.view.File_name


class FileAdaptor(private var Files: List<String>): RecyclerView.Adapter<FileAdaptor.viewHolder>() {

    var mStorage = FirebaseStorage.getInstance().reference

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var subjName = itemView.File_name
        var downloadBtn = itemView.DownloadFILE
        var deleteFile = itemView.DeleteFile
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val iemView = LayoutInflater.from(parent.context).inflate(R.layout.subject_view, parent, false)
        return viewHolder(iemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        holder.subjName.text = Files[position]
        holder.downloadBtn.setOnClickListener {
            holder.itemView.context.startActivity(Intent(holder.itemView.context,NoteViewPage::class.java).putExtra("fName",Files[position]).putExtra("sFname",Files[position]))
            (holder.itemView.context as Activity).finish()
        }

        holder.deleteFile.setOnClickListener {
            mStorage.child(Home.shared_user+"/"+Files[position]+".pdf").delete().addOnSuccessListener {
                holder.itemView.context.startActivity(Intent(holder.itemView.context,NoteViewPage::class.java).putExtra("dFname",Files[position]))
                Toast.makeText(holder.itemView.context, "File Deleted...", Toast.LENGTH_SHORT).show()
                (holder.itemView.context as Activity).finish()
            }
        }

//        holder.subjContainer.setOnClickListener {
//            Toast.makeText(holder.itemView.context, "Downloading...", Toast.LENGTH_SHORT).show()
//            val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(Files[position]+""))
//            request.setTitle(Files[position])
//            request.setMimeType("application/pdf")
//            request.allowScanningByMediaScanner()
//            request.setAllowedOverMetered(true)
//            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,Files[position]+".pdf")
////            val dm : DownloadManager = getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager
////            dm.enqueue(request)
//        }
    }

    override fun getItemCount(): Int {
        return Files.size
    }



}