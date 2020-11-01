package com.ss.moviehub

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ss.moviehub.Fragments.LibraryFragment
import com.ss.moviehub.Fragments.MoviesFragment
import com.ss.moviehub.Fragments.SearchFragment
import com.ss.moviehub.Models.Result
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val moviesFragment: Fragment = MoviesFragment()
    private val searchFragment: Fragment = SearchFragment()
    private val libraryFragment: Fragment = LibraryFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentFragment(moviesFragment)
        transferData()
    }

    private fun currentFragment(fragment: Fragment) {
        navigation_bar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_icon -> currentFragment(moviesFragment)
                R.id.search_icon -> currentFragment(searchFragment)
                R.id.library_icon -> currentFragment(libraryFragment)
                else -> Toast.makeText(this, "Not Yet Implemented...", Toast.LENGTH_SHORT).show()
            }
            true
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun transferData() {
        val intent = intent
        val library = intent.getBundleExtra("libraryIntent")
        val result = library?.getParcelable<Result>("library")

        val bundle = Bundle()
        bundle.putParcelable("library", result)
        libraryFragment.arguments = bundle
    }
}