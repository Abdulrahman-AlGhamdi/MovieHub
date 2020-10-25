package com.ss.moviehub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ss.moviehub.API.MovieAPI
import com.ss.moviehub.Fragments.MoviesFragment
import com.ss.moviehub.Fragments.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var popularPosterList = mutableListOf<String>()
var popularTitleList = mutableListOf<String>()
var popularBackdropList = mutableListOf<String>()
var popularReleaseDateList = mutableListOf<String>()
var popularOverviewList = mutableListOf<String>()
var popularVoteAverageList = mutableListOf<Double>()

var topRatedPosterList = mutableListOf<String>()
var topRatedTitleLists = mutableListOf<String>()
var topRatedBackdropLists = mutableListOf<String>()
var topRatedReleaseDateLists = mutableListOf<String>()
var topRatedOverviewLists = mutableListOf<String>()
var topRatedVoteAverageLists = mutableListOf<Double>()

var upcomingPosterList = mutableListOf<String>()
var upcomingTitleLists = mutableListOf<String>()
var upcomingBackdropLists = mutableListOf<String>()
var upcomingReleaseDateLists = mutableListOf<String>()
var upcomingOverviewLists = mutableListOf<String>()
var upcomingVoteAverageLists = mutableListOf<Double>()

var searchPosterList = mutableListOf<String>()
var searchTitleLists = mutableListOf<String>()
var searchBackdropLists = mutableListOf<String>()
var searchReleaseDateLists = mutableListOf<String>()
var searchOverviewLists = mutableListOf<String>()
var searchVoteAverageLists = mutableListOf<Double>()

class MainActivity : AppCompatActivity() {

    private val moviesFragment: Fragment = MoviesFragment()
    private val searchFragment: Fragment = SearchFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentFragment(moviesFragment)

        navigation_bar.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_icon -> currentFragment(moviesFragment)
                R.id.search_icon -> currentFragment(searchFragment)
                else -> currentFragment(moviesFragment)
            }
            true
        }
    }

    private fun currentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}