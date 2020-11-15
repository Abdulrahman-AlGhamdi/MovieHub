package com.ss.moviehub.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ss.moviehub.Adapters.RecyclerAdapter
import com.ss.moviehub.Models.Result
import com.ss.moviehub.R

var libraryListHolder: ArrayList<Result> = ArrayList()

class LibraryFragment : Fragment() {

    private lateinit var libraryView: View
    private lateinit var libraryList: MutableList<Result>
    private lateinit var libraryRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        libraryView = inflater.inflate(R.layout.fragment_library, container, false)

        initViews()
        bindLibraryMovies()

        return libraryView
    }

    private fun initViews() {
        libraryList = mutableListOf()
        libraryRecyclerView = libraryView.findViewById(R.id.library_movies)
        libraryRecyclerView.layoutManager = GridLayoutManager(context, 3)
    }

    private fun bindLibraryMovies() {
        libraryList.clear()
        libraryListHolder.forEach {
            libraryList.add(it)
        }
        libraryRecyclerView.adapter = RecyclerAdapter(libraryList, "LibraryFragment")
    }
}