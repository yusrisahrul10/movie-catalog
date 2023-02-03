package com.yusrish.catalogmovie.screen.moviedetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yusrish.catalogmovie.data.remote.model.movie.ResultMovie
import com.yusrish.catalogmovie.data.remote.model.video.ResultVideo
import com.yusrish.catalogmovie.data.remote.model.video.VideoResponse
import com.yusrish.catalogmovie.databinding.ActivityMovieDetailBinding
import com.yusrish.catalogmovie.screen.ViewModelFactory
import com.yusrish.catalogmovie.screen.movie.LoadingStateAdapter
import com.yusrish.catalogmovie.screen.moviedetail.adapter.ReviewAdapter
import com.yusrish.catalogmovie.screen.moviedetail.adapter.VideoAdapter
import com.yusrish.catalogmovie.utils.Constant
import com.yusrish.catalogmovie.utils.Constant.GenresMap
import com.yusrish.catalogmovie.utils.NetworkResult
import com.yusrish.catalogmovie.utils.Utils.dateFormat
import kotlinx.coroutines.launch

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    private val movieDetailViewModel: MovieDetailViewModel by viewModels {
        ViewModelFactory(this)
    }

    private lateinit var videoAdapter: VideoAdapter

    companion object {
        private const val DETAIL_MOVIE = "GENRE_ID"
        fun newIntent(
            context: Context,
            movie: ResultMovie
        ): Intent {
            return Intent(context, MovieDetailActivity::class.java)
                .putExtra(DETAIL_MOVIE, movie)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initData()
    }

    private fun initData() {
        val detailMovie = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(DETAIL_MOVIE, ResultMovie::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(DETAIL_MOVIE)
        }

        var genres = ""

        binding.apply {
            Glide.with(this@MovieDetailActivity)
                .load(Constant.BASE_IMAGE_URL + detailMovie?.posterPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgPoster)

            Glide.with(this@MovieDetailActivity)
                .load(Constant.BASE_IMAGE_URL + detailMovie?.backdropPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgBackground)

            tvTitle.text = detailMovie?.title
            tvRelease.text = dateFormat(
                detailMovie?.releaseDate,
                "yyyy-mm-dd",
                "dd MMMM yyyy"
            )
            tvPopularity.text = detailMovie?.popularity.toString() + " Viewers"
            tvRating.text = detailMovie?.voteAverage.toString()
            tvDescription.text = detailMovie?.overview

            detailMovie?.genreIds?.forEach {
                genres = "$genres${GenresMap.getValue(it)}, "
            }

            tvGenres.text = genres.dropLast(2)

            btnRetryVideo.setOnClickListener {
                getVideo(detailMovie)
            }
            btnRetryReview.setOnClickListener {
                getReview(detailMovie)
            }


        }
        getReview(detailMovie)
        getVideo(detailMovie)
    }

    private fun initDataVideo(videoResponse: VideoResponse) {
        if (videoResponse.resultVideo.isNotEmpty()) {
            videoAdapter = VideoAdapter(this, videoResponse.resultVideo) { item -> onVideoClicked(item) }
            binding.rvVideo.apply {
                layoutManager = LinearLayoutManager(
                    this@MovieDetailActivity,
                    LinearLayoutManager.HORIZONTAL, false
                )
                adapter = videoAdapter
            }
            binding.tvEmptyVideo.visibility = View.GONE
            binding.btnTrailer.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://${videoResponse.resultVideo[0].key}"))
                startActivity(intent)
            }
        } else {
            binding.tvEmptyVideo.visibility = View.VISIBLE
        }
    }

    private fun onVideoClicked(video: ResultVideo) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://${video.key}"))
        startActivity(intent)
//        startActivity(PlayerActivity.newIntent(this, video.key))
    }

    private fun getReview(detailMovie: ResultMovie?) {
        val reviewAdapter = ReviewAdapter(this)
        binding.rvReview.apply {
            layoutManager = LinearLayoutManager(this@MovieDetailActivity)
            adapter = reviewAdapter.withLoadStateFooter(footer = LoadingStateAdapter {
                reviewAdapter.retry()
            })
        }

        movieDetailViewModel.getReview(detailMovie?.id ?: 0).observe(this) {
            reviewAdapter.submitData(lifecycle, it)
        }

        reviewAdapter.addLoadStateListener { loadState ->
            binding.progressBarReview.isVisible = loadState.source.refresh is LoadState.Loading
            binding.rvReview.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.btnRetryReview.isVisible = loadState.source.refresh is LoadState.Error
            handleError(loadState)
        }
    }

    private fun handleError(loadState: CombinedLoadStates) {
        val errorState = loadState.source.refresh as? LoadState.Error

        errorState?.let {
            Toast.makeText(this, "${it.error}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getVideo(detailMovie: ResultMovie?) {
        lifecycleScope.launchWhenCreated {
            launch {
                movieDetailViewModel.getVideo(detailMovie?.id ?: 0).collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            initDataVideo(result.data!!)
                            binding.progressBarVideo.visibility = View.GONE
                            binding.btnRetryVideo.visibility = View.GONE
                            binding.rvVideo.visibility = View.VISIBLE
                        }
                        is NetworkResult.Loading -> {
                            binding.progressBarVideo.visibility = View.VISIBLE
                            binding.btnRetryVideo.visibility = View.INVISIBLE
                            binding.rvVideo.visibility = View.INVISIBLE
                        }
                        is NetworkResult.Error -> {
                            binding.progressBarVideo.visibility = View.GONE
                            binding.btnRetryVideo.visibility = View.VISIBLE
                            binding.rvVideo.visibility = View.GONE
                            Toast.makeText(this@MovieDetailActivity, result.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

}