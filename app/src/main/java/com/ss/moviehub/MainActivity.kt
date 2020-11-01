package com.ss.moviehub

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ss.moviehub.Fragments.DetailsFragment
import com.ss.moviehub.Fragments.LibraryFragment
import com.ss.moviehub.Fragments.MoviesFragment
import com.ss.moviehub.Fragments.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val moviesFragment: Fragment = MoviesFragment()
    private val searchFragment: Fragment = SearchFragment()
    private val libraryFragment: Fragment = LibraryFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addingFragment()
        currentFragment()
    }

    private fun addingFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, moviesFragment)
            .add(R.id.fragment_container, searchFragment)
            .add(R.id.fragment_container, libraryFragment)
            .commit()
    }

    private fun currentFragment() {
        setFragment(moviesFragment)
        navigation_bar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_icon -> setFragment(moviesFragment)
                R.id.search_icon -> setFragment(searchFragment)
                R.id.library_icon -> setFragment(libraryFragment)
                else -> Toast.makeText(this, "Not Yet Implemented...", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    private fun setFragment(fragment: Fragment) {
        when (fragment) {
            is MoviesFragment -> {
                supportFragmentManager.beginTransaction()
                    .hide(searchFragment)
                    .hide(libraryFragment)
                    .show(fragment).commit()
            }
            is SearchFragment -> {
                supportFragmentManager.beginTransaction()
                    .hide(moviesFragment)
                    .hide(libraryFragment)
                    .show(fragment).commit()
            }
            is LibraryFragment -> {
                supportFragmentManager.beginTransaction()
                    .hide(searchFragment)
                    .hide(moviesFragment)
                    .show(fragment).commit()
            }
        }
    }
}