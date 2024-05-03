package com.example.pirateflix.moviesactivity

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
import com.example.pirateflix.databinding.ActivityShowAllMoviesBinding
import com.example.pirateflix.databinding.Top100moviesItemBinding
import com.example.pirateflix.model.Top100MoviesModelItem
import com.example.pirateflix.viewmodel.MainViewModel
import com.example.pirateflix.viewmodel.ShowAllMoviesViewModel

class ShowAllMoviesActivity : AppCompatActivity() ,OnClickListener{
    lateinit var binding: ActivityShowAllMoviesBinding
    lateinit var viewModel: ShowAllMoviesViewModel
    private lateinit var top100MoviesAdapter: Top100MoviesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityShowAllMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBackBtn.setOnClickListener(this)

        setProgressBar()
        viewModel = ViewModelProvider(this).get(ShowAllMoviesViewModel::class.java)

        top100MoviesAdapter = Top100MoviesAdapter()
        prepareTopMoviesRecyclerview()
        viewModel.getTopMovies()
        observeTopMoviesLiveData()
        ontopMovieItemClick()

    }

    private fun ontopMovieItemClick() {
        top100MoviesAdapter.onItemClick={movie->
            val intent=Intent(this@ShowAllMoviesActivity, ContentDetailsActivity::class.java)
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
            val mealList = value
            top100MoviesAdapter.setMovies(mealList as ArrayList<Top100MoviesModelItem>)
            binding.pbLogin.isVisible=false
            Log.d("TopMoviesApiFromMainActivity", mealList.toString())
        }
    }

    private fun prepareTopMoviesRecyclerview() {
        binding.rvPopularMovies.apply {
            layoutManager =
                GridLayoutManager(this@ShowAllMoviesActivity,3, GridLayoutManager.VERTICAL, false)
            adapter = top100MoviesAdapter
        }
    }


    override fun onClick(v: View?) {
        when(v){
            binding.ivBackBtn->{
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}