package com.example.pirateflix.moviesactivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pirateflix.MainActivity
import com.example.pirateflix.R
import com.example.pirateflix.adapter.Top100MoviesAdapter
import com.example.pirateflix.adapter.TopSeriesAdapter
import com.example.pirateflix.databinding.ActivityShowAllSeriesBinding
import com.example.pirateflix.model.TopSeriesModelItem
import com.example.pirateflix.viewmodel.ShowAllMoviesViewModel
import com.example.pirateflix.viewmodel.ShowAllSeriesViewModel

class ShowAllSeriesActivity : AppCompatActivity(), OnClickListener {
    lateinit var binding: ActivityShowAllSeriesBinding
    lateinit var viewModel: ShowAllSeriesViewModel
    private lateinit var topSeriesAdapter: TopSeriesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowAllSeriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setProgressBar()

        binding.ivBackBtn.setOnClickListener(this)
        topSeriesAdapter= TopSeriesAdapter()
        viewModel = ViewModelProvider(this).get(ShowAllSeriesViewModel::class.java)
        prepareTopSeriesRecyclerview()
        viewModel.getTopSeries()
        observeTopSeriesLiveData()
        ontopSeriesItemClick()
    }

    private fun ontopSeriesItemClick() {
        topSeriesAdapter.onItemClick = { series ->
            val intent=Intent(this@ShowAllSeriesActivity, ContentDetailsActivity::class.java)
            intent.putExtra("id",series.id)
            intent.putExtra("from","series")
            startActivity(intent)
        }
    }

    private fun observeTopSeriesLiveData() {
        viewModel.observeTopSeries().observe(
            this
        ) { value ->
            val series = value
            topSeriesAdapter.setSeriesList(series as ArrayList<TopSeriesModelItem>)
            binding.pbLogin.isVisible = false
            Log.d("TopMoviesApiFromMainActivity", series.toString())
        }
    }

    private fun prepareTopSeriesRecyclerview() {
        binding.rvPopularSeries.apply {
            layoutManager =
                GridLayoutManager(this@ShowAllSeriesActivity,3, GridLayoutManager.VERTICAL, false)
            adapter = topSeriesAdapter

        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.ivBackBtn -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun setProgressBar() {
        binding.pbLogin.setMinFrame(10)
        binding.pbLogin.setMaxFrame(50)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}