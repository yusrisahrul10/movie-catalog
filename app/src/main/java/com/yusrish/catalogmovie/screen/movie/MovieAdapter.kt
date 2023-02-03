package com.yusrish.catalogmovie.screen.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yusrish.catalogmovie.data.remote.model.movie.ResultMovie
import com.yusrish.catalogmovie.databinding.ItemMovieBinding
import com.yusrish.catalogmovie.utils.Constant

class MovieAdapter(
    private val context: Context,
    private val listener: (ResultMovie) -> Unit
) : PagingDataAdapter<ResultMovie, MovieAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data, context, listener)
        }
    }

    class ViewHolder(private val view: ItemMovieBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(data: ResultMovie, context: Context, listener: (ResultMovie) -> Unit) {
            view.run {
                Glide.with(context)
                    .load(Constant.BASE_IMAGE_URL + data.posterPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgPoster)

                tvTitle.text = data.originalTitle
                tvRating.text = data.voteAverage.toString()

                root.setOnClickListener {
                    listener(data)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultMovie>() {
            override fun areItemsTheSame(oldItem: ResultMovie, newItem: ResultMovie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ResultMovie, newItem: ResultMovie): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}