package com.ss.moviehub.Adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.ss.moviehub.Fragments.DetailsFragment
import com.ss.moviehub.MainActivity
import com.ss.moviehub.Models.Result
import com.ss.moviehub.R

class RecyclerAdapter(private val movieItems: List<Result>): RecyclerView.Adapter<ViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.d("resultMovie", "onBindViewHolder: has been called")

        val bundle = Bundle()
        val detailsFragment: Fragment = DetailsFragment()
        val movieItem = movieItems[position]

        Glide.with(holder.poster)
            .load("https://image.tmdb.org/t/p/w342${movieItem.poster_path}")
            .transform(CenterCrop())
            .into(holder.poster)

        holder.itemView.setOnClickListener {
            bundle.putParcelable("details", movieItem)
            detailsFragment.arguments = bundle
            (holder.itemView.context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailsFragment)
                .addToBackStack(null).commit()
        }
    }
}