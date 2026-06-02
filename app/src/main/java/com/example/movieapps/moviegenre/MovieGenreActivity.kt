package com.example.movieapps.moviegenre

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapps.MovieIntent
import com.example.movieapps.MovieState
import com.example.movieapps.MovieViewModel
import com.example.movieapps.R
import com.example.movieapps.Status
import com.example.movieapps.databinding.ActivityMainBinding
import com.example.movieapps.movielist.MovieListActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieGenreActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MovieViewModel by viewModels()
    private val genreAdapter by lazy{ GenresAdapter(
        onGenreClick = { genre ->
            val intent = Intent(
                this,
                MovieListActivity::class.java
            )

            intent.putExtra(
                "genre_id",
                genre.id
            )

            startActivity(intent)
        }
    ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        with(binding.rvGenre) {
            adapter = genreAdapter
            animation = null
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        viewModel.processIntent(
            MovieIntent.LoadGenres
        )
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    render(it)
                }
            }
        }

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
    }

    private fun render(state: MovieState) {
        when (state.status) {
            Status.Loading -> {
                // Show loading state
            }
            Status.SuccessLoadGenres -> {
                genreAdapter.submitList(state.genres)
            }
            is Status.Error -> {

            }
            else -> TODO()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}