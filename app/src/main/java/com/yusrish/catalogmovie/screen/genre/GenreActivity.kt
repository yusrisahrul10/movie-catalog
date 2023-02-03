package com.yusrish.catalogmovie.screen.genre

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.yusrish.catalogmovie.data.remote.model.genre.GenreResponse
import com.yusrish.catalogmovie.data.remote.model.genre.Genres
import com.yusrish.catalogmovie.databinding.ActivityGenreBinding
import com.yusrish.catalogmovie.screen.ViewModelFactory
import com.yusrish.catalogmovie.screen.movie.MovieActivity
import com.yusrish.catalogmovie.utils.NetworkResult
import kotlinx.coroutines.launch

class GenreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGenreBinding

    private val genreViewModel: GenreViewModel by viewModels {
        ViewModelFactory(this)
    }

    private lateinit var genreAdapter: GenreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        getGenre()

        binding.btnRetry.setOnClickListener {
            getGenre()
        }
    }

    private fun getGenre() {
        lifecycleScope.launchWhenCreated {
            launch {
                genreViewModel.getGenreForMovie().collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            initData(result.data!!)
                            binding.progressBar.visibility = View.GONE
                            binding.btnRetry.visibility = View.GONE
                            binding.rvGenre.visibility = View.VISIBLE
                        }
                        is NetworkResult.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.rvGenre.visibility = View.GONE
                            binding.btnRetry.visibility = View.GONE
                        }
                        is NetworkResult.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.rvGenre.visibility = View.GONE
                            binding.btnRetry.visibility = View.VISIBLE
                            Toast.makeText(this@GenreActivity, result.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun initData(genreResponse: GenreResponse) {
        if (genreResponse.genres.isNotEmpty()) {
            genreAdapter = GenreAdapter(this, genreResponse.genres) { item -> onGenreClicked(item) }
            binding.rvGenre.apply {
                layoutManager = GridLayoutManager(this@GenreActivity, 2)
                adapter = genreAdapter
            }
        }
    }

    private fun onGenreClicked(genres: Genres) {
        startActivity(MovieActivity.newIntent(this, genres.id))
    }
}