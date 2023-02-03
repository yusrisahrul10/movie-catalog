package com.yusrish.catalogmovie.screen.moviedetail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yusrish.catalogmovie.data.remote.model.review.ResultReview
import com.yusrish.catalogmovie.databinding.ItemReviewBinding
import com.yusrish.catalogmovie.utils.Constant
import com.yusrish.catalogmovie.utils.Utils

class ReviewAdapter(
    private val context: Context
) : PagingDataAdapter<ResultReview, ReviewAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data, context)
        }
    }

    class ViewHolder(private val view: ItemReviewBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(data: ResultReview, context: Context) {
            view.run {
                Glide.with(context)
                    .load(Constant.BASE_IMAGE_URL + data.authorDetails.avatarPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgPoster)

                tvAuthor.text = data.author
                tvDate.text = Utils.dateFormat(
                    data.createdAt,
                    "yyyy-mm-dd",
                    "dd MMMM yyyy"
                )
                tvReview.text = data.content
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultReview>() {
            override fun areItemsTheSame(oldItem: ResultReview, newItem: ResultReview): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ResultReview, newItem: ResultReview): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}