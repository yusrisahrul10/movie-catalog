package com.yusrish.catalogmovie.screen.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusrish.catalogmovie.data.remote.model.movie.ResultMovie
import com.yusrish.catalogmovie.databinding.ActivityMovieBinding
import com.yusrish.catalogmovie.screen.ViewModelFactory
import com.yusrish.catalogmovie.screen.moviedetail.MovieDetailActivity

class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding

    private val movieViewModel: MovieViewModel by viewModels {
        ViewModelFactory(this)
    }

    private var genreId: Int = 0

    companion object {
        private const val GENRE_ID = "GENRE_ID"
        fun newIntent(
            context: Context,
            genreId: Int
        ): Intent {
            return Intent(context, MovieActivity::class.java)
                .putExtra(GENRE_ID, genreId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
    }

    private fun getData() {
        val adapter = MovieAdapter(this) { item -> onMovieClicked(item) }
        genreId = intent.getIntExtra(GENRE_ID, 0)

        adapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.rvMovie.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.btnRetry.isVisible = loadState.source.refresh is LoadState.Error
            handleError(loadState)
        }

        binding.btnRetry.setOnClickListener {
            adapter.retry()
        }

        binding.rvMovie.adapter = adapter.withLoadStateFooter(footer = LoadingStateAdapter {
                    adapter.retry()
        })
        binding.rvMovie.layoutManager = LinearLayoutManager(this@MovieActivity)
        movieViewModel.getMovie(genreId).observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun handleError(loadState: CombinedLoadStates) {
        val errorState = loadState.source.refresh as? LoadState.Error

        errorState?.let {
            Toast.makeText(this, "${it.error}", Toast.LENGTH_SHORT).show()
        }
    }


    private fun onMovieClicked(movie: ResultMovie) {
        startActivity(MovieDetailActivity.newIntent(this, movie))
    }
}