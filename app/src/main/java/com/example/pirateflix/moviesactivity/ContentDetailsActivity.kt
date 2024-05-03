package com.example.pirateflix.moviesactivity

import android.content.ActivityNotFoundException
import android.content.ContentProvider
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.pirateflix.MainActivity
import com.example.pirateflix.databinding.ActivityContentDetailsBinding
import com.example.pirateflix.viewmodel.ContentDetailsViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class ContentDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityContentDetailsBinding
    lateinit var viewModel: ContentDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setProgressBar()
        binding.pbMovieDetials.isVisible = true
        binding.rlMovieDetails.isVisible = false
        val intent = intent
        var movieId = intent.getStringExtra("id")
        var from = intent.getStringExtra("from")
        Log.d("MOvieId", movieId.toString())

        lifecycle.addObserver(binding.youtubePlayerView)

        viewModel = ViewModelProvider(this)[ContentDetailsViewModel::class.java]

        if (from.equals("movie")) {
            viewModel.getMovieDetails(movieId!!)
            observeMovieDetails()
        } else if (from.equals("series")) {
            viewModel.getSeriesDetails(movieId!!)
            observeSeriesDetails()
        }


    }

    private fun setProgressBar() {
        binding.pbMovieDetials.setMinFrame(10)
        binding.pbMovieDetials.setMaxFrame(50)
    }

    private fun observeSeriesDetails() {
        viewModel.observeSeriesDetails().observe(this, Observer { series ->

            binding.pbMovieDetials.isVisible = false
            binding.rlMovieDetails.isVisible = true
            binding.tvTitle.setText(series.title)
            binding.tvreleasYear.setText(series.year.toString())
            binding.tvRating.setText(series.rating.toString())
            binding.tvDesc.setText(series.description)
            binding.tvImdbLink.setText(series.imdb_link)
            Glide.with(this).load(series.big_image).into(binding.imageView)
            Log.e("YouTubePlayer", "Error: ${series.trailer_youtube_id}")

            binding.youtubePlayerView.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = series.trailer_youtube_id
                    youTubePlayer.loadVideo(videoId, 0f)
                    youTubePlayer.play()
                }
                override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                    super.onError(youTubePlayer, error)
                    Log.e("YouTubePlayer", "Error: $error")
                    // Handle error
                }
            })

            binding.tvImdbLink.setOnClickListener{
                val url = series.imdb_link
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.`package` = "com.android.chrome" // Package name of Chrome
                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    // Chrome is not installed, open the default browser
                    intent.`package` = null
                    startActivity(intent)
                }
            }
        })
    }

    private fun observeMovieDetails() {
        viewModel.observeMovieDetails().observe(this, Observer { movie ->
            binding.pbMovieDetials.isVisible = false
            binding.rlMovieDetails.isVisible = true
            binding.tvTitle.setText(movie.title)
            binding.tvreleasYear.setText(movie.year.toString())
            binding.tvRating.setText(movie.rating.toString())
            binding.tvDesc.setText(movie.description)
            binding.tvImdbLink.setText(movie.imdb_link)
            Glide.with(this).load(movie.big_image).into(binding.imageView)
            Log.e("YouTubePlayer", "Error: ${movie.trailer_youtube_id}")

            binding.youtubePlayerView.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = movie.trailer_youtube_id
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            })

            binding.tvImdbLink.setOnClickListener{
                val url = movie.imdb_link
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.`package` = "com.android.chrome" // Package name of Chrome
                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    // Chrome is not installed, open the default browser
                    intent.`package` = null
                    startActivity(intent)
                }
            }
        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}