package com.ss.moviehub.Fragments

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

private var libraryList: MutableList<Result> = mutableListOf()

class LibraryFragment : Fragment() {

    private lateinit var libraryView: View
    private lateinit var bundle: Bundle
    private lateinit var libraryRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        libraryView = inflater.inflate(R.layout.fragment_library, container, false)

        initViews()
        bindLibraryMovies()
        libraryRecyclerView.adapter = RecyclerAdapter(libraryList)

        return libraryView
    }

    private fun initViews() {
        libraryRecyclerView = libraryView.findViewById(R.id.library_movies)
        libraryRecyclerView.layoutManager = GridLayoutManager(context, 3)
    }

    private fun bindLibraryMovies() {
        if (this.arguments != null) {
            bundle = this.arguments!!
            libraryList.clear()
            val library: Result? = bundle.getParcelable("library")
            if (library != null)
                libraryList.add(library)
        }
    }
}