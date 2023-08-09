package com.ss.moviehub.screen.library

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MovieFilter
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ss.moviehub.R
import com.ss.moviehub.data.models.Result
import com.ss.moviehub.ui.common.DefaultCenterTopAppBar
import com.ss.moviehub.ui.common.DefaultIcon
import com.ss.moviehub.ui.common.DefaultIconButton
import com.ss.moviehub.ui.common.DefaultScaffold
import com.ss.moviehub.ui.common.DefaultText
import com.ss.moviehub.ui.common.DefaultTextButton
import com.ss.moviehub.ui.theme.MovieHubTheme

@Composable
fun LibraryScreen(
    librariesMovies: List<Result>,
    onAddClick: () -> Unit,
    onDeleteAllCLick: () -> Unit,
    onMovieClick: (Result) -> Unit
) {
    var deleteDialog by remember { mutableStateOf(false) }

    DefaultScaffold(topBar = { MoviesTopAppBar { deleteDialog = true } }) {
        if (librariesMovies.isNotEmpty()) LibraryMoviesList(
            librariesMovies = librariesMovies,
            onMovieClick = onMovieClick
        ) else EmptyLibrary(onAddClick = onAddClick)
    }

    if (deleteDialog) DeleteAllDialog(
        onAccept = { onDeleteAllCLick(); deleteDialog = false },
        onDismiss = { deleteDialog = false }
    )
}

@Composable
private fun MoviesTopAppBar(onDeleteAllClick: () -> Unit) = DefaultCenterTopAppBar(
    title = R.string.library_screen,
    actions = { DefaultIconButton(onClick = onDeleteAllClick, icon = Icons.Default.Delete) }
)

@Composable
private fun EmptyLibrary(onAddClick: () -> Unit) = Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    DefaultIcon(imageVector = Icons.Default.MovieFilter, modifier = Modifier.size(50.dp))
    DefaultText(text = R.string.library_empty, modifier = Modifier.padding(top = 16.dp))
    DefaultTextButton(text = R.string.library_add_movies, onClick = onAddClick)
}

@Composable
private fun LibraryMoviesList(
    librariesMovies: List<Result>,
    onMovieClick: (Result) -> Unit
) = LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
    items(key = { it.id }, items = librariesMovies) {
        LibraryMovieItem(movie = it, onMovieClick = onMovieClick)
    }
}

@Composable
private fun LibraryMovieItem(movie: Result, onMovieClick: (Result) -> Unit) = Row(
    modifier = Modifier
        .height(150.dp)
        .padding(horizontal = 16.dp)
        .clickable(onClick = { onMovieClick(movie) }),
    verticalAlignment = Alignment.CenterVertically
) {
    AsyncImage(
        model = "https://image.tmdb.org/t/p/w342${movie.posterPath}",
        contentDescription = null,
    )
    ListItem(
        headlineContent = { Text(text = movie.title) },
        supportingContent = { Text(text = movie.releaseDate) }
    )
}

@Composable
private fun DeleteAllDialog(onAccept: () -> Unit, onDismiss: () -> Unit) = AlertDialog(
    onDismissRequest = onDismiss,
    icon = { DefaultIcon(imageVector = Icons.Default.Delete, modifier = Modifier.size(50.dp)) },
    title = { DefaultText(text = R.string.library_delete_all_title) },
    text = { DefaultText(text = R.string.library_delete_all_message) },
    confirmButton = { DefaultTextButton(text = R.string.ok, onClick = onAccept) },
    dismissButton = { DefaultTextButton(text = R.string.cancel, onClick = onDismiss) }
)

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "AR")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "EN")
private fun LibraryScreenPreview() = MovieHubTheme {
    LibraryScreen(
        librariesMovies = emptyList(),
        onAddClick = {},
        onDeleteAllCLick = {},
        onMovieClick = {}
    )
}

//import android.os.Bundle
//import android.view.Menu
//import android.view.MenuInflater
//import android.view.MenuItem
//import android.view.View
//import androidx.appcompat.app.AlertDialog
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.lifecycleScope
//import androidx.navigation.fragment.findNavController
//import androidx.recyclerview.widget.ItemTouchHelper
//import androidx.recyclerview.widget.RecyclerView
//import com.ss.moviehub.R
//import com.ss.moviehub.databinding.FragmentLibraryBinding
//import com.ss.moviehub.utils.navigateTo
//import com.ss.moviehub.utils.showSnackBar
//import com.ss.moviehub.utils.viewBinding
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.flow.collect
//import kotlinx.coroutines.launch
//
//@AndroidEntryPoint
//class LibraryFragment : Fragment(R.layout.fragment_library) {
//
//    private val binding by viewBinding(FragmentLibraryBinding::bind)
//    private val viewModel: LibraryViewModel by viewModels()
//    private val adapter = LibraryAdapter()
//    private lateinit var libraryJob: Job
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        showLibraryMovies()
//        deleteFromLibrary()
//    }
//
//    private fun showLibraryMovies() {
//        libraryJob = lifecycleScope.launch(Dispatchers.Main) {
//            viewModel.getLibraryMovies().collect {
//                if (it.isEmpty()) {
//                    binding.libraryHeader.visibility = View.GONE
//                    binding.libraryList.visibility   = View.GONE
//                    binding.empty.visibility         = View.VISIBLE
//
//                    binding.emptyButton.setOnClickListener {
//                        val directions = LibraryFragmentDirections
//                        val action = directions.actionLibraryFragmentToMoviesFragment()
//                        findNavController().navigateTo(action, R.id.libraryFragment)
//                    }
//                } else {
//                    setHasOptionsMenu(true)
//                    adapter.differ.submitList(it)
//                    binding.empty.visibility         = View.GONE
//                    binding.libraryHeader.visibility = View.VISIBLE
//                    binding.libraryList.visibility   = View.VISIBLE
//                    binding.libraryList.adapter      = adapter
//                }
//            }
//        }
//    }
//
//    private fun deleteFromLibrary() {
//        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
//            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
//            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//        ) {
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val result = adapter.differ.currentList[viewHolder.adapterPosition]
//                viewModel.removeMovie(result)
//
//                requireView().showSnackBar(
//                    message = getString(R.string.successfully_deleted),
//                    actionMessage = getString(R.string.undo)
//                ) { viewModel.addMovie(result) }
//            }
//
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean = true
//        }
//
//        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.libraryList)
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.library_menu, menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.delete_all_menu)
//            AlertDialog.Builder(requireActivity()).apply {
//                this.setTitle(getString(R.string.delete_all_movies))
//                this.setCancelable(false)
//                this.setNegativeButton(getString(R.string.cancel), null)
//                this.setPositiveButton(getString(R.string.ok)) { _, _ -> viewModel.deleteAllMovies() }
//                this.create()
//            }.show()
//        return super.onOptionsItemSelected(item)
//    }
//
//    override fun onDestroyView() {
//        if (::libraryJob.isInitialized) libraryJob.cancel()
//        super.onDestroyView()
//    }
//}