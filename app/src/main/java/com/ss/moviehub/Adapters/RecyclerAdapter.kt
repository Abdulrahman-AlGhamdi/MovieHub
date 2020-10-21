package com.ss.moviehub.Adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.ss.moviehub.R

class RecyclerAdapter(private var posterPath: List<String>): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posterPath.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.poster)
            .load("https://image.tmdb.org/t/p/w342${posterPath[position]}")
            .transform(CenterCrop())
            .into(holder.poster)
    }
}

 class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)

    init {
        itemView.setOnClickListener{
            val position = adapterPosition
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.themoviedb.org/")
            startActivity(itemView.context, intent, null)
        }
    }
}