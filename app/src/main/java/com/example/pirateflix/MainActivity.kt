package com.example.pirateflix

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pirateflix.adapter.Top100MoviesAdapter
import com.example.pirateflix.adapter.TopSeriesAdapter
import com.example.pirateflix.authentication.LoginActivity
import com.example.pirateflix.databinding.ActivityMainBinding
import com.example.pirateflix.model.Top100MoviesModelItem
import com.example.pirateflix.model.TopSeriesModelItem
import com.example.pirateflix.moviesactivity.ContentDetailsActivity
import com.example.pirateflix.moviesactivity.ShowAllMoviesActivity
import com.example.pirateflix.moviesactivity.ShowAllSeriesActivity
import com.example.pirateflix.viewmodel.LoginViewModel
import com.example.pirateflix.viewmodel.MainViewModel
import com.example.pirateflix.viewmodel.ShowAllMoviesViewModel
import com.example.pirateflix.viewmodel.ShowAllSeriesViewModel
import com.google.firebase.auth.FirebaseAuth
import com.sg.magicpaybusiness.utils.AppConstants
import com.sg.magicpaybusiness.utils.AppPrefs
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity(), OnClickListener {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private lateinit var top100MoviesAdapter: Top100MoviesAdapter
    private lateinit var topSeriesAdapter: TopSeriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        enableEdgeToEdge()
        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

//        hideNavigationBar()

        setProgressBar()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        getBannerImages()
        top100MoviesAdapter = Top100MoviesAdapter()
        topSeriesAdapter = TopSeriesAdapter()
        prepareTopMoviesRecyclerview()
        viewModel.getTopMovies()
        observeTopMoviesLiveData()
        ontopMovieItemClick()

        prepareTopSeriesRecyclerview()
        viewModel.getTopSeries()
        observeTopSeries()
        ontopSeriesItemClick()


        binding.tvShowMore.setOnClickListener(this)
        binding.tvShowMoreSeries.setOnClickListener(this)
        binding.btnLogout.setOnClickListener(this)
    }

    private fun ontopSeriesItemClick() {
        topSeriesAdapter.onItemClick = { series ->
            val intent=Intent(this@MainActivity, ContentDetailsActivity::class.java)
            intent.putExtra("id",series.id)
            intent.putExtra("from","series")
            startActivity(intent)
        }
    }

    private fun observeTopSeries() {
        viewModel.observeTopSeries().observe(
            this
        ) { value ->
            val series = value
            topSeriesAdapter.setSeriesList(series as ArrayList<TopSeriesModelItem>)
            binding.pbLogin.isVisible = false
            binding.svMain.isVisible = true
            Log.d("TopMoviesApiFromMainActivity", series.toString())
        }
    }

    private fun prepareTopSeriesRecyclerview() {
        binding.rvPopularSeries.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = topSeriesAdapter

        }
    }

    private fun ontopMovieItemClick() {
        top100MoviesAdapter.onItemClick = { movie ->
            val intent=Intent(this@MainActivity, ContentDetailsActivity::class.java)
            intent.putExtra("id",movie.id)
            intent.putExtra("from","movie")

            startActivity(intent)

        }
    }

    private fun setProgressBar() {
        binding.pbLogin.setMinFrame(10)
        binding.pbLogin.setMaxFrame(50)
    }

    private fun observeTopMoviesLiveData() {
        viewModel.observeTopMovies().observe(
            this
        ) { value ->
            val movies = value
            top100MoviesAdapter.setMovies(movies as ArrayList<Top100MoviesModelItem>)
            binding.pbLogin.isVisible = false
            binding.svMain.isVisible = true
            Log.d("TopMoviesApiFromMainActivity", movies.toString())
        }
    }

    private fun prepareTopMoviesRecyclerview() {
        binding.rvPopularMovies.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = top100MoviesAdapter
        }
    }

    private fun getBannerImages() {

        viewModel.getBannerImages()
        observeBannerImages()
    }

    private fun observeBannerImages() {
        viewModel.observeImages().observe(this, Observer { images ->


        })
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.tvShowMore -> {
                startActivity(Intent(this, ShowAllMoviesActivity::class.java))
                finish()
            }

            binding.btnLogout -> {
                FirebaseAuth.getInstance().signOut()
                AppPrefs.putBooleanPref(AppConstants.IS_LOGIN,false,this)
                startActivity(Intent(this,LoginActivity::class.java))
                finish()

            }

            binding.tvShowMoreSeries -> {
                startActivity(Intent(this, ShowAllSeriesActivity::class.java))
                finish()
            }
        }
    }

//    private fun hideNavigationBar() {
//        val decorView: View = this.window.decorView
//        val uiOptions: Int = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
//        val timer = Timer()
//        val task: TimerTask = object : TimerTask() {
//            override fun run() {
//                runOnUiThread { decorView.systemUiVisibility = uiOptions }
//            }
//        }
//        timer.scheduleAtFixedRate(task, 1, 2)
//    }
}