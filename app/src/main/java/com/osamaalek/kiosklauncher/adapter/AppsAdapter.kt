package com.osamaalek.kiosklauncher.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.osamaalek.kiosklauncher.R
import com.osamaalek.kiosklauncher.model.AppInfo
import com.osamaalek.kiosklauncher.ui.AuthActivity

class AppsAdapter(private val list: List<AppInfo>, private val context: Context) :
    RecyclerView.Adapter<AppsAdapter.ContentHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_app, parent, false)
        return ContentHolder(view)
    }

    override fun onBindViewHolder(holder: ContentHolder, position: Int) {
        val appInfo = list[position]
        holder.textView.text = appInfo.label
        holder.imageView.setImageDrawable(appInfo.icon)

        holder.itemView.setOnClickListener {
            val packageName = appInfo.packageName
            if (packageName != null) {
                val authIntent = Intent(context, AuthActivity::class.java)
                authIntent.putExtra("packageName", packageName.toString())
                try {
                    context.startActivity(authIntent)
                } catch (e: Exception) {
                    Toast.makeText(context, "Failed to start activity: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Package name is null", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = list.size

    class ContentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.app_icon)
        val textView: TextView = itemView.findViewById(R.id.app_name)
    }
}
