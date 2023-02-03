package com.yusrish.catalogmovie.screen.genre

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yusrish.catalogmovie.data.remote.model.genre.Genres
import com.yusrish.catalogmovie.databinding.ItemGenreBinding

class GenreAdapter(
    private val context: Context,
    private val listStory: List<Genres>,
    private val listener: (Genres) -> Unit
) : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {


    class ViewHolder(private val view: ItemGenreBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(genres: Genres, context: Context, listener: (Genres) -> Unit) {
            val urlIcon = "https://media.istockphoto.com/id/1254949714/id/vektor/film-dokumenter-ikon-glyph-hitam-genre-film-umum-kategori-film-simbol-siluet-biopik-historis.jpg?s=612x612&w=0&k=20&c=KbtigyrvHPoF3jH8WdIOWUFpKQZZa00g8RhNXakv5dA="
            view.run {
                tvGenre.text = genres.name
                Glide.with(context)
                    .load(urlIcon)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgGenre)

                cvGenre.setOnClickListener {
                    listener(genres)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemGenreBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
    )

    override fun getItemCount(): Int = listStory.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listStory[position], context, listener)
    }
}