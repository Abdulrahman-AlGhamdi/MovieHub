package com.ss.moviehub.utils

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.google.android.material.snackbar.Snackbar
import com.ss.moviehub.R

fun View.showSnackBar(
    message: String,
    length: Int = Snackbar.LENGTH_SHORT,
    anchorView: Int = R.id.navigation_bar,
    actionMessage: String? = null,
    action: (View) -> Unit = {}
) {
    Snackbar.make(this, message, length).apply {
        actionMessage?.let { this.setAction(actionMessage) { action(it) } }
        this.setAnchorView(anchorView)
    }.show()
}

fun NavController.navigateTo(action: NavDirections, fragmentId: Int) {
    if (this.currentDestination == this.graph.findNode(fragmentId))
        this.navigate(action)
}