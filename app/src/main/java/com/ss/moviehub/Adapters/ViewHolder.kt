package com.ss.moviehub.Adapters

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ss.moviehub.R

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)
}