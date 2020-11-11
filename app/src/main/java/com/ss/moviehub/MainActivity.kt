package com.ss.moviehub

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ss.moviehub.Fragments.DetailsFragment
import com.ss.moviehub.Fragments.LibraryFragment
import com.ss.moviehub.Fragments.MoviesFragment
import com.ss.moviehub.Fragments.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation_bar.setupWithNavController(findNavController(R.id.fragment))
    }
}