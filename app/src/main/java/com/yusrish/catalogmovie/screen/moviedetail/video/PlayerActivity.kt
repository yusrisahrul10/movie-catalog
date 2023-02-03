package com.yusrish.catalogmovie.screen.moviedetail.video

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.yusrish.catalogmovie.databinding.ActivityPlayerBinding
import com.yusrish.catalogmovie.utils.Constant.YOUTUBE_VIDEO_URL

class PlayerActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityPlayerBinding.inflate(layoutInflater)
    }

    companion object {
        private const val KEY_VIDEO = "KEY_VIDEO"
        fun newIntent(
            context: Context,
            key: String
        ): Intent {
            return Intent(context, PlayerActivity::class.java)
                .putExtra(KEY_VIDEO, key)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val player = ExoPlayer.Builder(this).build()
        binding.videoView.player = player

        val key = intent.getStringExtra(KEY_VIDEO)

        val mediaItem = MediaItem.fromUri(YOUTUBE_VIDEO_URL + key)
        player.setMediaItem(mediaItem)
        player.prepare()
    }
}