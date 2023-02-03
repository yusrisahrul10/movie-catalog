package com.yusrish.catalogmovie.screen.moviedetail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yusrish.catalogmovie.data.remote.model.video.ResultVideo
import com.yusrish.catalogmovie.databinding.ItemVideoBinding
import com.yusrish.catalogmovie.utils.Constant.YOUTUBE_IMAGE_URL

class VideoAdapter(
    private val context: Context,
    private val listStory: List<ResultVideo>,
    private val listener: (ResultVideo) -> Unit
) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {


    class ViewHolder(private val view: ItemVideoBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(video: ResultVideo, context: Context, listener: (ResultVideo) -> Unit) {
            view.run {
                tvTitle.text = video.name
                itemView.also {
                    Glide.with(context)
                        .load(YOUTUBE_IMAGE_URL + video.key + "/maxresdefault.jpg")
                        .centerCrop()
                        .into(imgPoster)
                }

                root.setOnClickListener {
                    listener(video)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemVideoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
    )

    override fun getItemCount(): Int = listStory.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listStory[position], context, listener)
    }
}